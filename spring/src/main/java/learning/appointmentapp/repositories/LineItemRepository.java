package learning.appointmentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}