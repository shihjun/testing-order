package learning.appointmentapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.ProductRepository;

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepo;

  @Autowired
  ProductRepository productRepo;

  @Autowired
  LineItemRepository lineItemRepo;

  public List<LineItem> createOrder(List<Product> products, long[] quantity) {

    Boolean zeroQtyForAProduct = false;
    List<LineItem> lineItemList = new ArrayList<LineItem>();

    // check is 0 quantity for a product
    for (int j = 0; j < quantity.length; j++) {
      if (quantity[j] == 0) {
        zeroQtyForAProduct = true;
      }
    }

    // check invalid product, doesn't exist in DB
    if (products.size() != 0 && zeroQtyForAProduct != true) {
      // create order
      Order order = new Order();
      orderRepo.save(order);
      // create line_item
      for (int i = 0; i < products.size(); i++) {
        long lineItemPrice = products.get(i).getPrice() * quantity[i];
        LineItem lineItem = new LineItem();
        lineItem.setOrder(order);
        lineItem.setProduct(products.get(i));
        lineItem.setQuantity(quantity[i]);
        lineItem.setPrice(lineItemPrice);
        lineItemRepo.save(lineItem);
        lineItemList.add(lineItem);
      }
      return lineItemList;
    } else {
      return null;
    }
  }
}