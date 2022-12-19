package home.ecommerce.entity;

import home.ecommerce.service.StorageService;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "category_t")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private String cipher;
    private String fileName;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("subcategoryName asc")
    private Set<Subcategory> subcategories;

    @PreRemove
    public void deleteImage() {
        StorageService.deleteFile(fileName);
    }
}
