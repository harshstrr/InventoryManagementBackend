package com.inventory.management.SalesOrder.modal;

import com.inventory.management.SalesOrder.SalesOrderStatus;
import com.inventory.management.SalesOrderItem.modal.SalesOrderItem;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.Warehouse.modal.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sales_order")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "order_number", nullable = false , unique = true, length = 50)
    private String orderNumber;

    @Column(name = "customer_name", length = 150)
    private String customerName;

    @Column(name = "customer_contact" , length = 100)
    private String customerContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SalesOrderStatus status = SalesOrderStatus.PENDING;

    @Column(name = "order_date" , nullable = false)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by" , nullable = false)
    private AppUser createdBy;

    @Column(name = "created_at" , nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "salesOrder" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<SalesOrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate () {
        createdAt = LocalDateTime.now();
    }


}
