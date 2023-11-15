package org.example;
import java.util.*;

public class EcommercePlatform {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Product> products = new HashMap<>();
    private final Map<Integer, Order> orders = new HashMap<>();
    private final Map<Product, Integer> purchaseStatistics = new HashMap<>();
    private int usersIdCounter = 1, productsIdCounter = 1, ordersIdCounter = 1;

    public void registerUser(User user) {
        int userId = generateUserId();
        user.setId(userId);
        users.put(userId, user);
        adjustOrdersIdCounter();
    }

    public void registerUsers(User... users) {
        Arrays.stream(users).forEach(this::registerUser);
    }

    public void registerProduct(Product product) {
        int productId = generateProductId();
        product.setId(productId);
        products.put(productId, product);
    }

    public void registerProducts(Product... products) {
        Arrays.stream(products).forEach(this::registerProduct);
    }

    private void adjustOrdersIdCounter() {
        int registeredUsers = users.size();
        ordersIdCounter -= registeredUsers;
        ordersIdCounter = Math.max(1, ordersIdCounter);
    }

    public List<Product> listAvailableProductsSortedByName() {
        return products.values().stream()
                .filter(product -> product.getStock() > 0)
                .sorted(Product.nameComparator())
                .toList();
    }

    public List<Product> listAvailableProductsSortedByStock() {
        return products.values().stream()
                .filter(product -> product.getStock() > 0)
                .sorted(Product.stockComparator())
                .toList();
    }

    public List<Product> listAvailableProductsSortedByPrice() {
        return products.values().stream()
                .filter(product -> product.getStock() > 0)
                .sorted(Product.priceComparator())
                .toList();
    }

    public void makeOrder(Order order) {
        int orderId = generateOrderId();
        int userId = order.getUserId();

        order.setId(orderId);
        order.setUserId(userId);
        order.recalculateTotalPrice();
        order.getOrderDetails().forEach(((product, quantity) -> withdrawProduct(product, quantity)));
        orders.put(orderId, order);
    }

    public void makeOrderFromUserCart(User user) {
        Order order = user.makeOrderFromCart();
        makeOrder(order);
    }

    public List<Product> listAvailableProducts() {
        return products.values().stream().filter(product -> product.getStock() > 0).toList();
    }

    public List<Product> getRecommendations(int number) {
        return purchaseStatistics.entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(number)
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<User> listCustomers() {
        return users.values().stream().toList();
    }

    public List<Order> listOrders() {
        return orders.values().stream().toList();
    }

    private int generateUserId() {
        return usersIdCounter++;
    }

    private int generateProductId() {
        return productsIdCounter++;
    }

    private int generateOrderId() {
        return ordersIdCounter++;
    }

    private void withdrawProduct(Product product, int quantity) {
        product.setStock(product.getStock() - quantity);

        if (purchaseStatistics.containsKey(product)) {
            int newProductStatistics = purchaseStatistics.get(product) + quantity;
            purchaseStatistics.replace(product, newProductStatistics);
        } else {
            purchaseStatistics.put(product, quantity);
        }
    }
}