package learning.appointmentapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
  List<LineItem> findAllByOrder(Order order);

}