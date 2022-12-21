package home.ecommerce.dto;

import home.ecommerce.contoller.filter.SearchCriteria;
import home.ecommerce.contoller.filter.SearchOperation;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FilterDTO {
    private boolean search = false;
    private String destination = "all";
    private String categoryName;
    private String subcategoryName;
    private String nameContains;
    private Integer minPrice = 0;
    @Nullable
    private Integer maxPrice;
    private String sortBy = "price";
    private boolean asc = true;

    public List<SearchCriteria> toCriteriaList() {
        List<SearchCriteria> criteria = new ArrayList<>();

        switch (destination) {
            case "category" -> criteria.add(new SearchCriteria("category", categoryName,
                    SearchOperation.EQUAL.name()));
            case "subcategory" -> criteria.add(new SearchCriteria("subcategory", subcategoryName,
                    SearchOperation.EQUAL.name()));
        }

        if (subcategoryName != null) {
            criteria.add(new SearchCriteria("subcategoryName", subcategoryName, SearchOperation.EQUAL.name()));
        } else {
            if (categoryName != null)
                criteria.add(new SearchCriteria("categoryName", categoryName, SearchOperation.EQUAL.name()));
        }

        if (nameContains != null && !nameContains.isEmpty())
            criteria.add(new SearchCriteria("productName", nameContains, SearchOperation.CONTAINS.name()));

        if (maxPrice != null)
            criteria.add(new SearchCriteria("price", maxPrice, SearchOperation.LESS_THAN_EQUAL.name()));

        if (minPrice != null)
            criteria.add(new SearchCriteria("price", minPrice, SearchOperation.GREATER_THAN_EQUAL.name()));

        return criteria;
    }
}
