package com.marco.ultimabreweryservice.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID",strategy = GenerationType.AUTO)
 //   @GenericGenerator(name = "UUID", strategy = "org.hibernate.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
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
    private String beerStyle;

    private String name;

    private String surName;
}
