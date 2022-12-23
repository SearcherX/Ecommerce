package home.ecommerce.repository;

import home.ecommerce.entity.Bucket;
import home.ecommerce.entity.BucketItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BucketItemRepository extends CrudRepository<BucketItem, Long> {
    Set<BucketItem> findByBucket(Bucket bucket);
    void deleteAllByBucket(Bucket bucket);
}
