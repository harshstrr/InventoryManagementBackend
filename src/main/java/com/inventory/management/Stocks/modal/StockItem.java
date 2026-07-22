package com.inventory.management.Stocks.modal;

import com.inventory.management.Products.modal.Product;
import com.inventory.management.Warehouse.modal.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stock_item", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id" , "warehouse_id"}))
@AllArgsConstructor
@Builder
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id" , nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private Integer quantity = 0;

    @Version
    private Long version;

    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;

    @PreUpdate
    @PrePersist
    protected void touch() {
        updatedAt = LocalDateTime.now();
    }
}
