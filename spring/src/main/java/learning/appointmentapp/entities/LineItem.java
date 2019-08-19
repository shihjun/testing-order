package learning.appointmentapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "line_items")
public class LineItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "quantity")
  private long quantity;

  @Column(name = "price")
  private long price;

  @ManyToOne()
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne()
  @JoinColumn(name = "product_id")
  private Product product;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public long getQuantity() {
    return this.quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  public long getPrice() {
    return this.price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public Order getOrder() {
    return this.order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

}