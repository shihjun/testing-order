import { Entity, PrimaryGeneratedColumn, OneToMany } from "typeorm";
import { Line_Item } from "./Line_Item";
import { Payment } from "./Payment";

@Entity("orders")
export class Order {

  @PrimaryGeneratedColumn()
  id: number;

  @OneToMany(type => Line_Item, line_item => line_item.order)
  line_items: Line_Item[];

  @OneToMany(type => Payment, payment => payment.order)
  payments: Payment[];

}
