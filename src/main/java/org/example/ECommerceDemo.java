package org.example;
import java.io.PrintStream;
import java.util.*;

public class ECommerceDemo {

    static EcommercePlatform platform;
    static PrintStream console = System.out;

    public static void main(String[] args) {
        initializeEcommercePlatform();

        console.println("Ecommerce users:");
        platform.listCustomers().forEach(user -> console.println(user.getUsername()));

        console.println("Ecommerce products (sorted by price):");
        platform.listAvailableProductsSortedByPrice().forEach(product -> console.println(product.toString()));

        console.println("Ecommerce products (sorted by name):");
        platform.listAvailableProductsSortedByName().forEach(product -> console.println(product.toString()));

        console.println("Ecommerce products (sorted by stock):");
        platform.listAvailableProductsSortedByStock().forEach(product -> console.println(product.toString()));

        console.println("Ecommerce orders:");
        platform.listOrders().forEach(order -> {
            console.println(order.getId() + ") " + "Customer " + order.getUserId() + " ordered:");
            order.getOrderDetails().forEach(((product, quantity) ->
                    console.println(product.getName() + " - " + quantity)
            ));
            console.println("Total price: " + order.getTotalPrice());
        });

        console.println("Recommendations fot you:");
        platform.getRecommendations(3).forEach(product -> console.println(product.getName()));
    }

    private static void initializeEcommercePlatform() {
        platform = new EcommercePlatform();

        platform.registerUsers(
                new User("Ichika"),
                new User("Nino"),
                new User("Miku"),
                new User("Yotsuba"),
                new User("Itsuki"));

        platform.registerProducts(
                new Product("Sneakers", 7000, 50),
                new Product("Pants", 6500, 40),
                new Product("Book", 640, 25),
                new Product("Watering can", 980, 75),
                new Product("Pen", 650, 45));

        makeRandomOrder();
        makeRandomOrder();
        makeRandomOrder();
        makeRandomOrder();
        makeRandomOrder();
    }

    private final static Random random = new Random();

    private static void makeRandomOrder() {
            var randomUser = pickRandomInList(platform.listCustomers());
            randomUser.discardCart();
            var orderDetails = new HashMap<Product, Integer>();
            var randomProduct = pickRandomInList(platform.listAvailableProducts());
            var randomQuantity = random.nextInt(1, 3);
            while (!orderDetails.containsKey(randomProduct)) {
                orderDetails.put(randomProduct, randomQuantity);
                randomUser.addProductToCart(randomProduct, randomQuantity);
                randomProduct = pickRandomInList(platform.listAvailableProducts());
                randomQuantity = random.nextInt(1, 3);
            }
            platform.makeOrderFromUserCart(randomUser);
            orderDetails.keySet().forEach(product -> randomUser.removeProductFromCart(product));
    }

    private static <T> T pickRandomInList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}