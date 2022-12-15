package home.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "product_t")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String cipher;
    private String description;
    private Double price;
    private Boolean present;
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Subcategory subcategory;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Image> files;
}
