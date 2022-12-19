package home.ecommerce.service;

import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<Subcategory> listAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAllByOrderBySubcategoryNameAsc();
        subcategories.forEach(subcategory -> Hibernate.initialize(subcategory.getCategory()));
        return subcategories;
    }

    @Transactional
    public Set<Product> getProducts(Subcategory subcategory) {
        return subcategory.getProducts();
    }

    public Subcategory findById(Long id) {
        return subcategoryRepository.findById(id).orElse(null);
    }

    public Subcategory findByCipher(Set<Subcategory> subcategories, String cipher) {
        for (Subcategory subcategory: subcategories) {
            if (subcategory.getCipher().equals(cipher))
                return subcategory;
        }
        return null;
    }

    public Subcategory save(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory add(SubcategoryDTO subcategoryDTO) {
        String fileName = StorageService.uploadFile(subcategoryDTO.getFile());
        Subcategory subcategory = new Subcategory();
        modelMapper.map(subcategoryDTO, subcategory);
        subcategory.setFileName(fileName);
        return save(subcategory);
    }

    public Subcategory update(SubcategoryDTO subcategoryDTO, Long id) {
        Subcategory oldSubcategory = findById(id);
        String oldFileName = oldSubcategory.getFileName();
        String newFileName;

        try {
            newFileName = StorageService.uploadFile(subcategoryDTO.getFile());

            if (!Objects.equals(oldFileName, newFileName))
                StorageService.deleteFile(oldFileName);
        } catch (RuntimeException ignore) {
            newFileName = oldFileName;
        }

        Subcategory subcategory = new Subcategory();
        modelMapper.map(subcategoryDTO, subcategory);
        subcategory.setId(id);
        subcategory.setFileName(newFileName);

        return save(subcategory);
    }

    public SubcategoryDTO toDTO(Subcategory subcategory) {
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        modelMapper.map(subcategory, subcategoryDTO);
        return subcategoryDTO;
    }

    public void deleteSubcategory(Long id) {
        Optional<Subcategory> deleted = subcategoryRepository.findById(id);
        deleted.ifPresent(subcategoryRepository::delete);
    }

    public long count() {
        return subcategoryRepository.count();
    }

    @Transactional
    public List<Subcategory> findByCategory(Category category) {
        return subcategoryRepository.findSubcategoriesByCategory(category);
    }
}
