package learning.appointmentapp.repositories;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AppointmentRepositoryTest {

  @Autowired
  AppointmentRepository repo;

  @Autowired
  EmployeeRepository employeeRepo;

  @Test
  public void testFindAllEmpty() {
    repo.deleteAll();

    List<Appointment> result = repo.findAll();

    assertEquals(0, result.size());
  }

  @Test
  public void testFindAllNotEmpty() {
    LocalDateTime timeslot = LocalDateTime.now();
    seedAppointment(timeslot);

    // When: repo.findAll()
    List<Appointment> result = repo.findAll();

    // Then: get an array of the appointments
    assertEquals(1, result.size());

    Appointment retrieved = result.get(0);
    assertEquals(timeslot, retrieved.getTimeslot());
  }

  public void testFindByTimeslotBetween() {
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = LocalDateTime.now().plusHours(3);
    seedAppointment(LocalDateTime.now().plusMinutes(30));
    seedAppointment(LocalDateTime.now().plusMinutes(45));
    seedAppointment(LocalDateTime.now().plusHours(5));

    List<Appointment> results = repo.findByTimeslotBetween(startTime, endTime);

    assertEquals(2, results.size());
  }

  @Test
  public void testFindByEmployeeEmailContainsPresent() {
    // Given
    Employee employee = new Employee();
    employee.setName("John");
    employee.setEmail("john@gmail.com");
    employeeRepo.save(employee);
    Appointment seeded = seedAppointment(LocalDateTime.now());
    seeded.setEmployee(employee);
    repo.save(seeded);

    List<Appointment> results = repo.findByEmployeeEmailContains(employee.getEmail());

    assertEquals(1, results.size());
  }

  @Test
  public void testFindByEmployeeEmailContainsEmpty() {

  }

  public Appointment seedAppointment(LocalDateTime timeslot) {
    Appointment appointment = new Appointment();
    appointment.setTimeslot(timeslot);
    repo.save(appointment);
    return appointment;

  }

}