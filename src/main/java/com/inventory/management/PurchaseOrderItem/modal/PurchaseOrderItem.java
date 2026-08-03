package com.inventory.management.PurchaseOrderItem.modal;

import com.inventory.management.Products.modal.Product;
import com.inventory.management.PurchaseOrder.modal.PurchaseOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_item")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false )
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

    @Column(name = "qty_ordered" , nullable = false)
    private Integer qtyOrdered;


    @Column(name = "qty_received" , nullable = false)
    private Integer qtyReceived = 0;

    @Column(name = "unit_price" , nullable = false , precision = 12, scale = 2)
    private BigDecimal unitPrice;



}
