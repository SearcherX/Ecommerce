package home.ecommerce.service;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.SubcategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public List<Subcategory> listAllSubcategories() {
        return (List<Subcategory>) subcategoryRepository.findAll();
    }

    public Subcategory findById(Long id) {
        return subcategoryRepository.findById(id).orElse(null);
    }

    public Subcategory save(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory add(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = new Subcategory();
        modelMapper.map(subcategoryDTO, subcategory);
//        subcategory.setCategory(categoryService.findById(subcategoryDTO.getCategoryId()));
        return save(subcategory);
    }

    public SubcategoryDTO toDTO(Subcategory subcategory) {
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        modelMapper.map(subcategory, subcategoryDTO);
//        subcategoryDTO.setCategoryId(subcategory.getCategory().getId());
        return subcategoryDTO;
    }

    public void deleteSubcategory(Long id) {
        Optional<Subcategory> deleted = subcategoryRepository.findById(id);
        deleted.ifPresent(subcategoryRepository::delete);
    }
}
