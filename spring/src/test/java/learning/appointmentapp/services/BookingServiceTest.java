package learning.appointmentapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookingServiceTest {

  @Autowired
  BookingService bookingService;

  @Autowired
  AppointmentRepository appointmentRepo;

  @Autowired
  EmployeeRepository employeeRepo;

  @Test
  public void testCheckAppointment() {
    // Given
    // create an employee
    // create 2-3 appointments
    Employee employee = seedEmployee();
    seedAppointment(LocalDateTime.now().plusHours(1), employee);
    seedAppointment(LocalDateTime.now().plusHours(3), employee);
    seedAppointment(LocalDateTime.now().plusHours(5), employee);

    // When
    // run checkAppointment
    List<LocalDateTime> results = bookingService.checkAppointment(employee);

    // Then
    // result should be an array of LocalDateTime
    assertEquals(3, results.size());
    // TODO: check that the 3 elements in the array match the appointments we
    // created

  }

  @Test
  public void testBookAppointmentSuccess() {
    Employee employee = seedEmployee();
    LocalDateTime timeslot = LocalDateTime.now().plusHours(1);

    Appointment appointment = bookingService.bookAppointment(timeslot, employee);

    // Then: how do we know it is successful?
    assertNotEquals(null, appointment);
    assertNotEquals(null, appointment.getId());
    assertEquals(employee.getId(), appointment.getEmployee().getId());
    // count how many appointments are there pre insertion vs post insertion

  }

  @Test
  public void testBookAppointmentFail() {
    Employee employee = seedEmployee();
    LocalDateTime timeslot = LocalDateTime.now().plusHours(1);
    // there must an existing timeslot that clashes
    LocalDateTime clashingTimeslot = LocalDateTime.now();
    seedAppointment(clashingTimeslot, employee);

    Appointment appointment = bookingService.bookAppointment(timeslot, employee);

    // Then: how do we know it is successful?
    assertEquals(null, appointment);
  }

  Employee seedEmployee() {
    Employee employee = new Employee();
    employee.setName("John");
    employee.setEmail("john@gmail.com");
    employeeRepo.save(employee);

    return employee;
  }

  Appointment seedAppointment(LocalDateTime timeslot, Employee employee) {
    Appointment appointment = new Appointment();
    appointment.setTimeslot(timeslot);
    appointment.setEmployee(employee);
    appointmentRepo.save(appointment);
    return appointment;
  }

}