package home.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import home.ecommerce.service.StorageService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Component
@Table(name = "subcategory_t")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subcategoryName;
    private String cipher;
    private String fileName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("price asc")
    private Set<Product> products;

    @PreRemove
    public void deleteImage() {
        StorageService.deleteFile(fileName);
    }
}
