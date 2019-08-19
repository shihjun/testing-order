package learning.appointmentapp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  List<Appointment> findByTimeslotBetween(LocalDateTime startTime, LocalDateTime endTime);

  List<Appointment> findByEmployeeEmailContains(String email);

  List<Appointment> findAllByEmployee(Employee employee);

}