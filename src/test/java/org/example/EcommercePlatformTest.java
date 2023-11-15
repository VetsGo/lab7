package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class EcommercePlatformTest {
    private EcommercePlatform ecommercePlatform;

    @BeforeEach
    public void setUp() {
        ecommercePlatform = new EcommercePlatform();
    }

    @Test
    public void testRegisterUser() {
        User user = new User("User");
        ecommercePlatform.registerUser(user);
        assertTrue(ecommercePlatform.listCustomers().contains(user));
    }

    @Test
    public void testRegisterUsers() {
        User user1 = new User("User1");
        User user2 = new User("User2");
        ecommercePlatform.registerUsers(user1, user2);
        assertTrue(ecommercePlatform.listCustomers().contains(user1));
        assertTrue(ecommercePlatform.listCustomers().contains(user2));
    }

    @Test
    public void testRegisterProduct() {
        Product product = new Product("Product", 100, 10);
        ecommercePlatform.registerProduct(product);
        assertTrue(ecommercePlatform.listAvailableProducts().contains(product));
    }

    @Test
    public void testRegisterProducts() {
        Product product1 = new Product("Product1", 50, 20);
        Product product2 = new Product("Product2", 75, 15);
        ecommercePlatform.registerProducts(product1, product2);
        assertTrue(ecommercePlatform.listAvailableProducts().contains(product1));
        assertTrue(ecommercePlatform.listAvailableProducts().contains(product2));
    }

    @Test
    public void testListAvailableProductsSortedByName() {
        Product product1 = new Product("Product1", 50, 10);
        Product product2 = new Product("Product3", 75, 15);
        Product product3 = new Product("Product2", 100, 5);

        ecommercePlatform.registerProducts(product1, product2, product3);

        var sortedProducts = ecommercePlatform.listAvailableProductsSortedByName();
        assertEquals(product1, sortedProducts.get(0));
        assertEquals(product3, sortedProducts.get(1));
        assertEquals(product2, sortedProducts.get(2));
    }

    @Test
    public void testListAvailableProductsSortedByStock() {
        Product product1 = new Product("Product1", 50, 10);
        Product product2 = new Product("Product2", 75, 5);
        Product product3 = new Product("Product3", 100, 15);

        ecommercePlatform.registerProducts(product1, product2, product3);

        var sortedProducts = ecommercePlatform.listAvailableProductsSortedByStock();
        assertEquals(product2, sortedProducts.get(0));
        assertEquals(product1, sortedProducts.get(1));
        assertEquals(product3, sortedProducts.get(2));
    }

    @Test
    public void testListAvailableProductsSortedByPrice() {
        Product product1 = new Product("Product1", 50, 10);
        Product product2 = new Product("Product2", 75, 5);
        Product product3 = new Product("Product3", 100, 15);

        ecommercePlatform.registerProducts(product1, product2, product3);

        var sortedProducts = ecommercePlatform.listAvailableProductsSortedByPrice();
        assertEquals(product1, sortedProducts.get(0));
        assertEquals(product2, sortedProducts.get(1));
        assertEquals(product3, sortedProducts.get(2));
    }

    @Test
    public void testMakeOrder() {
        User user = new User("User");
        ecommercePlatform.registerUser(user);

        Product product = new Product("Product", 100, 10);
        ecommercePlatform.registerProduct(product);

        user.addProductToCart(product, 1);
        Order order = user.makeOrderFromCart();

        ecommercePlatform.makeOrder(order);
        assertTrue(ecommercePlatform.listOrders().contains(order));
    }

    @Test
    public void testMakeOrderFromUserCart() {
        User user = new User("User");
        ecommercePlatform.registerUser(user);

        Product product = new Product("Product", 100, 10);
        ecommercePlatform.registerProduct(product);

        user.addProductToCart(product, 1);

        ecommercePlatform.makeOrderFromUserCart(user);
        assertTrue(ecommercePlatform.listOrders().size() > 0);
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetRecommendations() {
        Product product1 = new Product("Product1", 50, 10);
        Product product2 = new Product("Product2", 75, 5);
        Product product3 = new Product("Product3", 100, 15);

        ecommercePlatform.registerProducts(product1, product2, product3);

        User user = new User("User");
        ecommercePlatform.registerUser(user);

        user.addProductToCart(product1, 1);
        ecommercePlatform.makeOrderFromUserCart(user);

        var recommendations = ecommercePlatform.getRecommendations(1);
        assertEquals(1, recommendations.size());
        assertTrue(recommendations.get(0).equals(product1) || recommendations.get(0).equals(product3));
    }
}