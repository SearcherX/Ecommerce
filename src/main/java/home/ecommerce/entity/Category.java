package home.ecommerce.entity;

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
    @OneToMany(mappedBy = "category")
    private Set<Subcategory> subcategories;
}
