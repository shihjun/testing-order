package learning.appointmentapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Payment;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.PaymentRepository;
import learning.appointmentapp.repositories.ProductRepository;

@Service
public class PaymentService {

  @Autowired
  OrderRepository orderRepo;

  @Autowired
  ProductRepository productRepo;

  @Autowired
  PaymentRepository paymentRepo;

  @Autowired
  LineItemRepository lineItemRepo;

  public Payment createPayment(List<Order> order) {

    if (order.size() != 0) {
      Order existingOrder = orderRepo.findById(order.get(0).getId()).orElse(new Order());
      List<LineItem> lineItems = lineItemRepo.findAllByOrder(existingOrder);
      long paymentAmount = 0;
      for (int i = 0; i < lineItems.size(); i++) {
        paymentAmount += lineItems.get(i).getPrice();
      }

      Payment payment = new Payment();
      payment.setOrder(existingOrder);
      payment.setAmount(paymentAmount);
      payment.setPaid(true);
      payment.setRefunded(false);
      paymentRepo.save(payment);
      return payment;
    } else {
      return null;
    }
  }

  public Payment refundPayment(Payment payment) {

    if (payment != null) {
      Payment existingPayment = paymentRepo.findById(payment.getId()).orElse(new Payment());
      if (existingPayment.getPaid() == true && existingPayment.getRefunded() == false) {
        existingPayment.setRefunded(true);
        paymentRepo.save(existingPayment);
        return existingPayment;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }
}