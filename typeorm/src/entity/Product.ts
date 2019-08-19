import { Entity, PrimaryGeneratedColumn, OneToMany, Column } from "typeorm";
import { Line_Item } from "./Line_Item";

@Entity("products")
export class Product {

  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @Column()
  price: number;

  @OneToMany(type => Line_Item, line_item => line_item.product)
  line_items: Line_Item[];

}
