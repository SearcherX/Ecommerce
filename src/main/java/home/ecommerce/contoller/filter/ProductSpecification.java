package home.ecommerce.contoller.filter;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ProductSpecification implements Specification<Product> {
    private final SearchCriteria searchCriteria;

    public ProductSpecification(final SearchCriteria searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();
        switch(Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case EQUAL:
                if (searchCriteria.getFilterKey().equals("categoryName")) {
                    Join<Product, Subcategory> subcategory = root.join("subcategory", JoinType.INNER);
                    Join<Subcategory, Category> category = subcategory.join("category", JoinType.INNER);
                    return cb.equal(cb.lower(category.get(searchCriteria.getFilterKey())), strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("subcategoryName")) {
                    Join<Product, Subcategory> subcategory = root.join("subcategory", JoinType.INNER);
                    return cb.equal(cb.lower(subcategory.get(searchCriteria.getFilterKey())), strToSearch);
                }
                return cb.equal(root.get(searchCriteria.getFilterKey()), strToSearch);
            case CONTAINS:
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), strToSearch);
            case GREATER_THAN_EQUAL:
                return cb.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()), strToSearch);
        }
        return null;
    }

}
