package home.ecommerce.service;

import home.ecommerce.entity.Bucket;
import home.ecommerce.entity.BucketItem;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.User;
import home.ecommerce.repository.BucketItemRepository;
import home.ecommerce.repository.BucketRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class BucketService {
    private final BucketRepository bucketRepository;
    private final BucketItemRepository itemRepository;
    private final ProductService productService;

    public Bucket findByUser(User user) {
        return bucketRepository.findByUser(user);
    }

    public Bucket getBucket(User user) {
        Bucket bucket = user.getBucket();

        if (bucket == null) {
            bucket = new Bucket();
            bucket.setUser(user);
        }
        return bucketRepository.save(bucket);
    }

    @Transactional
    public void addItemToBucket(Product product, User user) {
        Bucket bucket = getBucket(user);
        Set<BucketItem> bucketItems = bucket.getBucketItems();

        if (bucketItems == null) {
            bucketItems = new HashSet<>();
        }

        BucketItem bucketItem = new BucketItem();
        bucketItem.setProduct(product);
        bucketItem.setBucket(bucket);
        bucketItem.setTotalPrice(product.getPrice());
        bucketItem.setQuantity(1);
        bucketItems.add(bucketItem);

        bucket.setBucketItems(bucketItems);

        int totalItems = totalItems(bucket.getBucketItems());
        double totalPrice = totalPrice(bucket.getBucketItems());

        bucket.setTotalPrices(totalPrice);
        bucket.setTotalItems(totalItems);

        bucketRepository.save(bucket);
    }

    public Bucket updateItemInBucket(Long id, int quantity, User user) {
        Bucket bucket = user.getBucket();

        Set<BucketItem> bucketItems = bucket.getBucketItems();

        BucketItem item = findBucketItem(bucketItems, id);
        Product product = item.getProduct();

        item.setQuantity(quantity);
        item.setTotalPrice(quantity * product.getPrice());

        itemRepository.save(item);

        int totalItems = totalItems(bucketItems);
        double totalPrice = totalPrice(bucketItems);

        bucket.setTotalItems(totalItems);
        bucket.setTotalPrices(totalPrice);

        return bucketRepository.save(bucket);
    }

    @Transactional
    public void deleteItemFromBucket(Long id, User user) {
        Bucket bucket = user.getBucket();
        Set<BucketItem> bucketItems = bucket.getBucketItems();
        BucketItem bucketItem = findBucketItem(bucketItems, id);
        bucketItems.remove(bucketItem);
        itemRepository.delete(bucketItem);

        double totalPrice = totalPrice(bucketItems);
        int totalItems = totalItems(bucketItems);

        bucket.setBucketItems(bucketItems);
        bucket.setTotalItems(totalItems);
        bucket.setTotalPrices(totalPrice);

        bucketRepository.save(bucket);
    }

    public void increasePurchaseNumber(User user) {
        Bucket bucket = user.getBucket();
        Set<BucketItem> items = bucket.getBucketItems();
        List<Product> products = items.stream()
                .map(BucketItem::getProduct)
                .toList();
        products.forEach(product -> product.setPurchasesNumber(product.getPurchasesNumber() + 1));
        productService.saveAll(products);
    }

    public void deleteALl(User user) {
        Bucket bucket = user.getBucket();
        bucket.getBucketItems().clear();
        bucketRepository.save(bucket);
    }

    private BucketItem findBucketItem(Set<BucketItem> bucketItems, Long itemId) {
        if (bucketItems == null) {
            return null;
        }
        BucketItem bucketItem = null;

        for (BucketItem item : bucketItems) {
            if (Objects.equals(item.getId(), itemId)) {
                bucketItem = item;
            }
        }
        return bucketItem;
    }

    private int totalItems(Set<BucketItem> cartItems){
        int totalItems = 0;
        for(BucketItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrice(Set<BucketItem> cartItems){
        double totalPrice = 0.0;

        for(BucketItem item : cartItems){
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
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
