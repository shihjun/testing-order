package learning.appointmentapp.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

@Service
public class BookingService {
  @Autowired
  AppointmentRepository appointmentRepo;

  @Autowired
  EmployeeRepository employeeRepo;

  public List<LocalDateTime> checkAppointment(Employee employee) {
    // get all the appointments for the employee
    List<Appointment> appointments = appointmentRepo.findAllByEmployee(employee);

    // extract the timeslot from each appointment
    List<LocalDateTime> timeslots = new ArrayList<LocalDateTime>();
    for (int i = 0; i < appointments.size(); i++) {
      timeslots.add(appointments.get(i).getTimeslot());
    }

    // return the list of timeslots
    return timeslots;
  }

  public Appointment bookAppointment(LocalDateTime timeslot, Employee employee) {
    LocalDateTime startTime = timeslot.minusHours(2);
    LocalDateTime endTime = timeslot.plusHours(2);

    List<Appointment> results = appointmentRepo.findByTimeslotBetween(startTime, endTime);

    if (results.size() == 0) {
      Appointment appointment = new Appointment();
      appointment.setEmployee(employee);
      appointment.setTimeslot(timeslot);
      appointmentRepo.save(appointment);
      return appointment;
    } else {
      return null;
    }

  }

}