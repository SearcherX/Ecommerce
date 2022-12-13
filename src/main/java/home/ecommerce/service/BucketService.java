package home.ecommerce.service;

import home.ecommerce.entity.Bucket;
import home.ecommerce.entity.BucketItem;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.User;
import home.ecommerce.repository.BucketItemRepository;
import home.ecommerce.repository.BucketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class BucketService {
    private final BucketRepository bucketRepository;
    private final BucketItemRepository itemRepository;
    private final UserService userService;

    public Bucket findByUser(User user) {
        return bucketRepository.findByUser(user);
    }

    public Set<BucketItem> listAllItems(User user) {
        Bucket bucket = bucketRepository.findByUser(user);

        if (bucket == null)
            return null;

        return itemRepository.findByBucket(bucket);
    }

    public Bucket addItemToBucket(Product product, User user) {
        Bucket bucket = user.getBucket();

        if (bucket == null) {
            bucket = new Bucket();
        }
        Set<BucketItem> bucketItems = bucket.getBucketItem();
        BucketItem bucketItem = findBucketItem(bucketItems, product.getId());

        if (bucketItems == null) {
            bucketItems = new HashSet<>();

            if (bucketItem == null) {
                bucketItem = new BucketItem();
                bucketItem.setProduct(product);
                bucketItem.setBucket(bucket);
                bucketItem.setTotalPrice(product.getPrice());
                bucketItem.setQuantity(1);
                bucketItems.add(bucketItem);
                itemRepository.save(bucketItem);
            }
        } else {
            if (bucketItem == null) {
                bucketItem = new BucketItem();
                bucketItem.setProduct(product);
                bucketItem.setBucket(bucket);
                bucketItem.setTotalPrice(product.getPrice());
                bucketItem.setQuantity(1);
                bucketItems.add(bucketItem);
                itemRepository.save(bucketItem);
            } else {
//                bucketItem.setQuantity(cartItem.getQuantity() + quantity);
//                cartItem.setTotalPrice(cartItem.getTotalPrice() + ( quantity * product.getCostPrice()));
                itemRepository.save(bucketItem);
            }
        }
        bucket.setBucketItem(bucketItems);
        bucket.setUser(user);

        return bucketRepository.save(bucket);
    }

    private BucketItem findBucketItem(Set<BucketItem> bucketItems, Long productId) {
        if (bucketItems == null) {
            return null;
        }
        BucketItem bucketItem = null;

        for (BucketItem item : bucketItems) {
            if (Objects.equals(item.getProduct().getId(), productId)) {
                bucketItem = item;
                break;
            }
        }
        return bucketItem;
    }

    public static boolean containsProduct(Set<BucketItem> items, Product product) {
        if (items == null)
            return false;

        for (BucketItem item : items) {
            if (item.getProduct().getId().equals(product.getId()))
                return true;
        }
        return false;
    }
}
