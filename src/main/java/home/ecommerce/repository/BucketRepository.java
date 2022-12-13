package home.ecommerce.repository;

import home.ecommerce.entity.Bucket;
import home.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface BucketRepository extends CrudRepository<Bucket, Long> {
    Bucket findByUser(User user);
}
