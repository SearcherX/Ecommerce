package home.ecommerce.service;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final StorageService storageService;

    public List<Image> listAllImages() {
        return (List<Image>) imageRepository.findAll();
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public void add(MultipartFile file, Product product) {
        if (Objects.equals(file.getOriginalFilename(), ""))
            return;

        String newFileName = storageService.uploadFile(file);
        Image image = new Image();
        image.setFileName(newFileName);
        image.setProduct(product);
        save(image);
    }

    public void update(MultipartFile file, Long id, Product product) {
        if (Objects.equals(file.getOriginalFilename(), ""))
            return;

        String newFileName = storageService.uploadFile(file);
        Image image = new Image();
        image.setFileName(newFileName);
        image.setProduct(product);
        image.setId(id);
        save(image);
    }

    public void delete(Long id) {
        Optional<Image> deleted = imageRepository.findById(id);
        deleted.ifPresent(image -> {
            imageRepository.delete(image);
            storageService.deleteFile(image.getFileName());
        });
    }
}
