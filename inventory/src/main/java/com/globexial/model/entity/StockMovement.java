package com.globexial.model.entity;


import com.globexial.model.enums.MovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "stock_movements")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private String referenceNumber;

    private String description;

    private Timestamp createdAt;

}


