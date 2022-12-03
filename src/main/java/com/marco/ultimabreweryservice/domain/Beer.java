package com.marco.ultimabreweryservice.domain;

import com.marco.dtocommoninterface.model.BeerStyleEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID",strategy = GenerationType.AUTO)
 //   @GenericGenerator(name = "UUID", strategy = "org.hibernate.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column( length = 36, updatable = false,nullable = false, columnDefinition = "varchar(36)")
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column
    private Timestamp lastModifiedDate;
    private String beerName;

    @Column
    @Enumerated(EnumType.STRING)
    private BeerStyleEnum beerStyle;

    @Column(unique = true)
    private String upc;

    private BigDecimal price;
    private Integer minOnHand;
    private Integer quantityToBrew;
}
