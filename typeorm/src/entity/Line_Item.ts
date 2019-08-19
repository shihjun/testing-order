import { Column, Entity, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from "typeorm";
import { Order } from "./Order";
import { Product } from "./Product";

@Entity("line_items")
export class Line_Item {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    quantity: number;

    @Column()
    price: number;

    @ManyToOne(type => Order)
    @JoinColumn({ name: "order_id" })
    order;

    @ManyToOne(type => Product)
    @JoinColumn({ name: "product_id" })
    product;

}
