package home.ecommerce.dto;

import home.ecommerce.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubcategoryDTO {
    private String subcategoryName;
    private String cipher;
    private String fileName;
    private Category category;
}
