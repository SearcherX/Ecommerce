package home.ecommerce.service;

import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    public List<Subcategory> listAllSubcategories() {
        return ((List<Subcategory>) subcategoryRepository.findAll()).stream()
                .sorted((Comparator.comparing(o -> o.getCategory().getCategoryName()))).toList();
    }

    public Subcategory findById(Long id) {
        return subcategoryRepository.findById(id).orElse(null);
    }

    public Subcategory save(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory add(SubcategoryDTO subcategoryDTO) {
        String fileName = storageService.uploadFile(subcategoryDTO.getFile());
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
            newFileName = storageService.uploadFile(subcategoryDTO.getFile());

            if (!Objects.equals(oldFileName, newFileName))
                storageService.deleteFile(oldFileName);
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
        deleted.ifPresent(subcategory -> {
            subcategoryRepository.delete(subcategory);
            storageService.deleteFile(subcategory.getFileName());
        });
    }

    public long count() {
        return subcategoryRepository.count();
    }

    @Transactional
    public List<Subcategory> findByCategory(Category category) {
        return subcategoryRepository.findSubcategoriesByCategory(category);
    }
}
