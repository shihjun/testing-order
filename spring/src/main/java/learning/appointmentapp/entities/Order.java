package learning.appointmentapp.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "order")
  private Set<LineItem> line_items;

  @OneToMany(mappedBy = "order")
  private Set<Payment> payments;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<LineItem> getLine_items() {
    return this.line_items;
  }

  public void setLine_items(Set<LineItem> line_items) {
    this.line_items = line_items;
  }

  public Set<Payment> getPayments() {
    return this.payments;
  }

  public void setPayments(Set<Payment> payments) {
    this.payments = payments;
  }

}