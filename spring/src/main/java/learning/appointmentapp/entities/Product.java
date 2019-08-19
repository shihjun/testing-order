package learning.appointmentapp.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private long price;

  @OneToMany(mappedBy = "order")
  private Set<LineItem> line_items;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getPrice() {
    return this.price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public Set<LineItem> getLine_items() {
    return this.line_items;
  }

  public void setLine_items(Set<LineItem> line_items) {
    this.line_items = line_items;
  }

}