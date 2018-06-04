package sda.pl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    BigDecimal priceGross;
    BigDecimal priceNet;
    String priceSymbol;

    @Transient
    BigDecimal vat;
}