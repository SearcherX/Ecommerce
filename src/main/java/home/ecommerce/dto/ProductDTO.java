package home.ecommerce.dto;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDTO {
    private String productName;
    private String cipher;
    private String description;
    private Double price;
    private Boolean present;
    private Subcategory subcategory;
}
