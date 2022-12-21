package home.ecommerce.contoller.filter;

import home.ecommerce.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ProductSpecificationBuilder {
    private final List<SearchCriteria> params;

    public ProductSpecificationBuilder() {
        params = new ArrayList<>();
    }
    public Specification<Product> build() {
        if(params.size() == 0){
            return null;
        }

        Specification<Product> result = new ProductSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++){
            SearchCriteria criteria = params.get(i);
            result = Specification.where(result).and(new ProductSpecification(criteria));
        }
        return result;
    }
    public final ProductSpecificationBuilder with(final SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
