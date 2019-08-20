package learning.appointmentapp.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderServiceTest {

  @Autowired
  OrderService orderService;

  @Autowired
  OrderRepository orderRepo;

  @Autowired
  ProductRepository productRepo;

  @Autowired
  LineItemRepository lineItemRepo;

  @Test
  public void testCreateOrderSuccess() {
    // Given: product and quantity info
    seedProduct("handphone", 500);
    seedProduct("laptop", 1000);
    long[] quantity = { 2, 3 };

    // When: create order
    List<Product> productList = productRepo.findAll();
    List<LineItem> lineItem = orderService.createOrder(productList, quantity);

    // Then:
    for (int i = 0; i < productList.size(); i++) {
      long price = productList.get(i).getPrice() * quantity[i];
      Product retrievedProduct = productList.get(i);
      assertEquals(retrievedProduct.getId(), lineItem.get(i).getProduct().getId());
      assertEquals(quantity[i], lineItem.get(i).getQuantity());
      assertEquals(price, lineItem.get(i).getPrice());
    }
  }

  @Test
  public void testCreateOrderFail() {
    // Given: either empty product or 0 quantity
    // Empty product
    seedProduct("handphone", 500);
    seedProduct("laptop", 1000);
    // 0 quantity
    long[] quantity = { 2, 0 };

    // When: create order
    List<Product> productList = productRepo.findAll();
    List<LineItem> lineItem = orderService.createOrder(productList, quantity);

    // Then:
    assertEquals(null, lineItem);
  }

  public void seedProduct(String name, long price) {
    Product product = new Product();

    product.setName(name);
    product.setPrice(price);
    productRepo.save(product);
  }

}
