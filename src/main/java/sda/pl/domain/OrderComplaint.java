package sda.pl.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "orderSet")
@Table(name = "complaint")
public class OrderComplaint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    @Enumerated(value = EnumType.STRING)
    ComplaintStatus complaintStatus;

    @ManyToMany(mappedBy = "orderComplaintSet", cascade = CascadeType.ALL)
    // relacja wiele do wiele
            Set<Order> orderSet;


    public static enum Color {
        WHITE, BLACK, RED
    }
}