package learning.appointmentapp.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Payment;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.PaymentRepository;
import learning.appointmentapp.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaymentServiceTest {

  @Autowired
  PaymentService paymentService;

  @Autowired
  OrderRepository orderRepo;

  @Autowired
  ProductRepository productRepo;

  @Autowired
  LineItemRepository lineItemRepo;

  @Autowired
  PaymentRepository paymentRepo;

  @Test
  public void testCreatePaymentSuccess() {
    // Given: order with line items already created
    Product product1 = seedProduct("handphone", 500);
    Product product2 = seedProduct("laptop", 1000);
    List<Order> order = new ArrayList<Order>();
    order.add(seedOrder());
    LineItem lineItem1 = seedLineItem(product1, order.get(0), 2);
    LineItem lineItem2 = seedLineItem(product2, order.get(0), 3);

    // When: creating a payment
    Payment payment = paymentService.createPayment(order);

    // Then:
    long paymentAmount = lineItem1.getPrice() + lineItem2.getPrice();
    assertEquals(order.get(0).getId(), payment.getOrder().getId());
    assertEquals(true, payment.getPaid());
    assertEquals(false, payment.getRefunded());
    assertEquals(paymentAmount, payment.getAmount());
  }

  @Test
  public void testCreateOrderFail() {
    // Given: empty order
    List<Order> order = new ArrayList<Order>();

    // When: creating a payment
    Payment payment = paymentService.createPayment(order);

    // Then:
    assertEquals(null, payment);

  }

  Product seedProduct(String name, long price) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    productRepo.save(product);

    return product;
  }

  Order seedOrder() {
    Order order = new Order();
    orderRepo.save(order);

    return order;
  }

  LineItem seedLineItem(Product product, Order order, long quantity) {
    LineItem lineItem = new LineItem();
    lineItem.setOrder(order);
    lineItem.setProduct(product);
    lineItem.setQuantity(quantity);
    lineItem.setPrice(product.getPrice() * quantity);
    lineItemRepo.save(lineItem);

    return lineItem;
  }

}
