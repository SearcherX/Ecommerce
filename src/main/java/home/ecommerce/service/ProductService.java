package home.ecommerce.service;

import home.ecommerce.contoller.filter.ProductSpecification;
import home.ecommerce.contoller.filter.ProductSpecificationBuilder;
import home.ecommerce.contoller.filter.SearchCriteria;
import home.ecommerce.dto.FilterDTO;
import home.ecommerce.dto.ProductDTO;
import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.entity.User;
import home.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@Service
@PropertySource("classpath:page.properties")
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ImageService imageService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${leftDif}")
    private int leftDif;
    @Value("${rightDif}")
    private int rightDif;

    public Page<Product> findSortedAll(Integer offset) {
        Page<Product> products = productRepository.findByOrderByPrice(PageRequest.of(offset - 1, pageSize));
        setMainImage(products);
        return products;
    }

    @Transactional
    public Page<Product> findSortedAllWithSubcategory(Integer offset, Integer pageSize) {
        Page<Product> products = productRepository.findByOrderByPrice(PageRequest.of(offset - 1, pageSize));
        products.forEach(product -> Hibernate.initialize(product.getSubcategory()));
//        setMainImage(products);
        return products;
    }

    public Page<Product> findTest(String categoryName, Integer offset) {
        //return productRepository.findAll(ProductSpecification.inSubcategory(categoryName), PageRequest.of(offset - 1, pageSize));
        return null;
    }

    private Pageable getPageRequest(Integer offset, Integer pageSize, String sortBy, boolean isAscending) {
        if (!isAscending)
            return PageRequest.of(offset, pageSize, Sort.by(sortBy).descending());

        return PageRequest.of(offset, pageSize, Sort.by(sortBy));
    }

    @Transactional
    public Page<Product> findBySearchCriteria(FilterDTO filterDTO, Integer offset) {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
        List<SearchCriteria> criteriaList = filterDTO.toCriteriaList();

        if (criteriaList != null) {
            criteriaList.forEach(builder::with);
        }

        Page<Product> products = productRepository.findAll(builder.build(),
                getPageRequest(offset - 1, pageSize, filterDTO.getSortBy(), filterDTO.isAsc()));
        setMainImage(products);
        return products;
    }

    @Transactional
    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        assert product != null;
        Hibernate.initialize(product.getFiles());
        return product;
    }

    // метод загрузки товара со всеми зображениями
    @Transactional
    public Product findByCipherFully(String cipher) {
        Product product = productRepository.findByCipher(cipher);
        Hibernate.initialize(product.getFiles());
        Hibernate.initialize(product.getSubcategory().getCategory());
        return product;
    }

    // метод загрузки списка товаров только с главным изображением
    public Page<Product> findBySubcategoryWithMainImage(Subcategory subcategory, Integer offset) {
        Page<Product> products = productRepository.findBySubcategoryOrderByPrice(subcategory, PageRequest.of(offset - 1, pageSize));
        setMainImage(products);
        return products;
    }

    public Page<Product> findBySimpleFilterWithMainImage(String filter, Integer offset) {
        Page<Product> products = productRepository.findByProductNameContainsIgnoreCaseOrderByPrice(filter, PageRequest.of(offset - 1, pageSize));
        setMainImage(products);
        return products;
    }

    public List<Product> findTop10Popular() {
        List<Product> products = productRepository.findTop10ByOrderByPurchasesNumberDesc();
        setMainImage(products);
        return products;
    }

    public List<Product> findTop10New() {
        List<Product> products = productRepository.findTop10ByOrderByRegisterDateDesc();
        setMainImage(products);
        return products;
    }

    private void setMainImage(Iterable<Product> products) {
        for (Product product : products) {
            List<Image> images = imageService.findMainImageByProduct(product);
            Image mainImage;

            //если нет изображения, то указать на изображение "Нет изображения"
            if (images.size() == 0) {
                mainImage = new Image();
                mainImage.setProduct(product);
                mainImage.setFileName("no image.jpeg");
            } else
                mainImage = images.get(0);

            List<Image> files = new ArrayList<>();
            files.add(mainImage);
            product.setFiles(files);
        }
        ;
    }


    // метод формирования списка страниц для пагинации
    // пример [1,..., x-2, x-1, x, x+1, x+2, ...,last], где x - текущая страница, last - последняя
    public List<Integer> getPageNumbers(Page<Product> page, int currentPage) {
        int pageCount = page.getTotalPages();

        if (pageCount <= 1)
            return new ArrayList<>(List.of(1));

        if (currentPage <= 0 || currentPage > pageCount)
            throw new RuntimeException("Нет такой страницы");

        int startPage;
        int endPage;

        // добавить страницы слева
        startPage = Math.max(currentPage - leftDif, 2);

        // добавить страницы справа
        endPage = Math.min(currentPage + rightDif, pageCount - 1);

        List<Integer> pageNumbers = new ArrayList<>();
        // в начало добавить первую страницу
        pageNumbers.add(1);

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        // в конец добавить последнюю страницу
        if (pageCount > endPage)
            pageNumbers.add(pageCount);

        return pageNumbers;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        modelMapper.map(product, productDTO);
        return productDTO;
    }

    public Product add(ProductDTO productDTO) {
        Product product = new Product();
        modelMapper.map(productDTO, product);
        product.setPurchasesNumber(0);
        product.setRegisterDate(new Date());
        return save(product);
    }

    public Product update(ProductDTO productDTO, Long id) {
        Product product = new Product();
        modelMapper.map(productDTO, product);
        product.setId(id);
        return save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> deleted = productRepository.findById(id);
        deleted.ifPresent(productRepository::delete);
    }
}
