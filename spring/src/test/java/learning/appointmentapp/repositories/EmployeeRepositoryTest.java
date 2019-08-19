package learning.appointmentapp.repositories;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {

  @Autowired
  EmployeeRepository repo;

  @Test
  public void testFindAllEmpty() {
    // Given: there are no employees in the DB
    repo.deleteAll();

    // When: employeeRepository.findAll()
    List<Employee> result = repo.findAll();

    // Then: get and empty array
    assertEquals(0, result.size());
  }

  @Test
  public void testFindAllNotEmployee() {
    // Given: there are employees in the DB
    seedEmployee();

    // When: employeeRepository.findAll()
    List<Employee> result = repo.findAll();

    // Then: get an array of the employess
    // check the ids of the employess retrieved from DB
    assertEquals(2, result.size());

    Employee retrievedEmployee = result.get(0);
    assertEquals("John", retrievedEmployee.getName());
    assertEquals("john@gmail.com", retrievedEmployee.getEmail());
  }

  @Test
  public void testFindById() {
    // Given: there are employees in the DB
    seedEmployee();

    // When:
    Employee employee = repo.findById(seedEmployee().get(0).getId()).orElse(new Employee());

    // Then:
    assertEquals("John", employee.getName());
    assertEquals("john@gmail.com", employee.getEmail());
  }

  @Test
  public void testFindByEmail() {
    // Given: there are employees in the DB
    String email = seedEmployee().get(0).getEmail();

    // When:
    List<Employee> employee = repo.findByEmail(email);
    Employee retrievedEmployee = employee.get(0);

    // Then:
    assertEquals("John", retrievedEmployee.getName());
    assertEquals("john@gmail.com", retrievedEmployee.getEmail());
  }

  @Test
  public void testFindByName() {
    // Given: there are employees in the DB
    String name = seedEmployee().get(0).getName();

    // When:
    List<Employee> employee = repo.findByName(name);
    Employee retrievedEmployee = employee.get(0);

    // Then:
    assertEquals("John", retrievedEmployee.getName());
    assertEquals("john@gmail.com", retrievedEmployee.getEmail());
  }

  @Test
  public void testFindAllWithSorting() {
    // Given: there are employees in the DB
    seedEmployee();

    // When:
    List<Employee> result = repo.findAll(Sort.by(Sort.Order.desc("name")));

    // Then:
    Employee retrievedEmployee = result.get(1);
    assertEquals("John", retrievedEmployee.getName());
    assertEquals("john@gmail.com", retrievedEmployee.getEmail());
    Employee retrievedEmployee1 = result.get(0);
    assertEquals("Sam", retrievedEmployee1.getName());
    assertEquals("sam@gmail.com", retrievedEmployee1.getEmail());
  }

  public List<Employee> seedEmployee() {
    List<Employee> employeeList = new ArrayList<Employee>();

    Employee seeded = new Employee();
    seeded.setName("John");
    seeded.setEmail("john@gmail.com");
    repo.save(seeded);
    Employee seeded2 = new Employee();
    seeded2.setName("Sam");
    seeded2.setEmail("sam@gmail.com");
    repo.save(seeded2);

    employeeList.add(seeded);
    employeeList.add(seeded2);

    return employeeList;
  }

}
