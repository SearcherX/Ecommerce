package home.ecommerce.service;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String name;

    private static String UPLOAD_PATH;

    @Value("${upload.path}")
    public void setNameStatic(String name){
        StorageService.UPLOAD_PATH = name;
    }

    public static String uploadFile(MultipartFile file) {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(UPLOAD_PATH);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(UPLOAD_PATH + "/" + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return resultFilename;
        }

        throw new RuntimeException("Ошибка загрузки файла");
    }

    public void deleteFile(Category category) {
        for (Subcategory subcategory : category.getSubcategories()) {
            deleteFile(subcategory);
        }
    }

    public void deleteFile(Subcategory subcategory) {
        for (Product product : subcategory.getProducts()) {
            deleteFile(product);
        }
    }

    public void deleteFile(Product product) {
        for (Image image : product.getFiles()) {
            deleteFile(image);
        }
    }

    public void deleteFile(Image image) {
        deleteFile(image.getFileName());
    }

    public static void deleteFile(String fileName) {
        File file = new File(UPLOAD_PATH + "/" + fileName);
        if (file.exists())
            file.delete();
    }
}
