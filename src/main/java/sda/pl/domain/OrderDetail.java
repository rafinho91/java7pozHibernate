package sda.pl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn
    Product product;
    @ManyToOne
    @JoinColumn
    Order order;
    Long amount;
    Order.Price price;

    public OrderDetail(CartDetail cartDetail){
        this.price = cartDetail.getPrice();
        this.amount = cartDetail.getAmount();
        this.product = cartDetail.getProduct();
    }
}