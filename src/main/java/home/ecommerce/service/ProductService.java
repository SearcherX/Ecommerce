package home.ecommerce.service;

import home.ecommerce.dto.ProductDTO;
import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Value("${spring.data.web.pageable.default-page-size}")
    private Integer pageSize;

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findBySubcategory(Subcategory subcategory, Integer offset) {
        return productRepository.findAllBySubcategoryOrderByPrice(subcategory, PageRequest.of(offset - 1, pageSize));
    }

    // метод формирования списка страниц для пагинации
    // пример [x-2, x-1, x, x+1, last], где x - текущая страница, last - последняя
    public List<Long> getPageNumbers(Subcategory subcategory, long currentPage) {
        long productCount = productRepository.countBySubcategory(subcategory);
        long pageCount = productCount / pageSize + (productCount % pageSize == 0 ? 0 : 1);

        if (currentPage <= 0 || currentPage > pageCount)
            throw new RuntimeException("Нет такой страницы");

        long startPage;
        long endPage = currentPage + 1;

        // добавить страницы слева
        // если слева нельзя добавить страницы, то добавить справа
        if (currentPage - 2 < 1) {
            endPage += 1 - (currentPage - 2);
            startPage = 1;
        } else {
            startPage = currentPage - 2;
        }

        // добавить страницы справа
        // если справа нельзя добавить страницы, то добавить слева
        if (currentPage + 1 > pageCount - 1) {
            startPage -= (currentPage + 1) - pageCount + 1;
            endPage = currentPage;
        } else {
            if (currentPage >= endPage)
                endPage = currentPage + 1;
        }

        // проверить, чтобы страницы не вылезли за диапазон
        if (startPage < 1)
            startPage = 1;
        if (endPage > pageCount)
            endPage = pageCount;

        List<Long> pageNumbers = new ArrayList<>();
        for (long i = startPage; i <= endPage; i++) {
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

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        modelMapper.map(product, productDTO);
        return productDTO;
    }

    public Product add(ProductDTO productDTO) {
        Product product = new Product();
        modelMapper.map(productDTO, product);
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
