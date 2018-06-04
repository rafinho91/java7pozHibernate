package sda.pl.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "orderDetailSet")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime date;
    Price totalPrice;
    @ManyToOne
    @JoinColumn
    User user;

    @Column(name = "adres_wysylki")
    String cityName;
    boolean RODO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    Set<OrderDetail> orderDetailSet;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // join table do laczenia przez tabele dodatkowa
    @JoinTable(
            name = "order_complaint",
            // joinColumns nazwa kolumny w tabeli dodatkowej z kluczem do tabeli laczonej
            // + nazwa pola w encji z kluczem
            // po ktorym laczymy
            joinColumns=@JoinColumn(name="order_complaint_id", referencedColumnName="id"),
            // nazwa kolumny z kluczem glownym z enccji Order
            inverseJoinColumns=@JoinColumn(name="order_id")
    )
    Set<OrderComplaint> orderComplaintSet;


    public void addOrderDetail(OrderDetail od){
        if(orderDetailSet == null){
            orderDetailSet = new HashSet<>();
        }

        od.setOrder(this);
        orderDetailSet.add(od);

    }

    public void calculateTotalPrice(){
        totalPrice = new Price();
        totalPrice.setPriceGross(BigDecimal.ZERO);
        totalPrice.setPriceNet(BigDecimal.ZERO);
        totalPrice.setPriceSymbol("PLN");

        getOrderDetailSet().forEach(
                cd ->
                {
                    getTotalPrice().setPriceGross(getTotalPrice().getPriceGross()
                            .add(
                                    cd.getPrice().getPriceGross().multiply(new BigDecimal(cd.getAmount()))
                            )
                    );

                    getTotalPrice().setPriceNet(getTotalPrice().getPriceNet()
                            .add(
                                    cd.getPrice().getPriceNet().multiply(new BigDecimal(cd.getAmount()))
                            )
                    );
                }
        );
    }

    @Data
    @Embeddable
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Price {

        BigDecimal priceGross;
        BigDecimal priceNet;
        String priceSymbol;

        @Transient
        BigDecimal vat;
    }
}