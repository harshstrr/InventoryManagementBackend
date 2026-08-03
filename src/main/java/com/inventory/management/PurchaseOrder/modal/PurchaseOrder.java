package com.inventory.management.PurchaseOrder.modal;

import com.inventory.management.Products.modal.Product;
import com.inventory.management.PurchaseOrder.PurchaseOrderStatus;
import com.inventory.management.PurchaseOrderItem.modal.PurchaseOrderItem;
import com.inventory.management.Supplier.modal.Supplier;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.Warehouse.modal.Warehouse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_number", length = 50 , unique = true , nullable = false)
    private String poNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id" , nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id" , nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false , length = 20)
    private PurchaseOrderStatus status = PurchaseOrderStatus.DRAFT;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "expected_date" , nullable = false)
    private LocalDate expectedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private AppUser createdBy;

    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "purchaseOrder" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
    }
}
