package com.inventory.management.SalesOrder;

import com.inventory.management.Products.modal.Product;
import com.inventory.management.Products.repository.ProductRepository;
import com.inventory.management.PurchaseOrderItem.repository.SalesOrderItemRepository;
import com.inventory.management.SalesOrder.dto.SalesOrderRequest;
import com.inventory.management.SalesOrder.modal.SalesOrder;
import com.inventory.management.SalesOrder.repository.SalesOrderRepository;
import com.inventory.management.SalesOrderItem.dto.SalesOrderItemRequest;
import com.inventory.management.SalesOrderItem.modal.SalesOrderItem;
import com.inventory.management.Stocks.StockServices;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import com.inventory.management.Warehouse.modal.Warehouse;
import com.inventory.management.Warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SalesOrderService {
    private final SalesOrderRepository so_repo;
    private final SalesOrderItemRepository soi_repo;
    private final ProductRepository productRepository;
    private final AppUserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockServices stockServices;

    private String generateSoNumber() {
        return "SO-" + System.currentTimeMillis();   // simple for now — swap for a proper sequence later
    }

    @Transactional
    public SalesOrder createSales( SalesOrderRequest request) {
        Warehouse warehouse = warehouseRepository.findById(request.warehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        AppUser createdBy = userRepository.findById(request.createdBy())
                .orElseThrow(() -> new RuntimeException("User not found"));


        SalesOrder order = new SalesOrder();

        order.setOrderNumber(generateSoNumber());
        order.setCustomerName(request.customerName());
        order.setCustomerContact(request.customerContact());
        order.setWarehouse(warehouse);
        order.setOrderDate(request.orderDate());
        order.setCreatedBy(createdBy);

        for(SalesOrderItemRequest itemReq : request.items()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SalesOrderItem item = new SalesOrderItem();

            item.setSalesOrder(order);
            item.setProduct(product);
            item.setQty(itemReq.qty());
            item.setUnitPrice(itemReq.unitPrice());

            order.getItems().add(item);
        }

        SalesOrder savedOrder = so_repo.save(order);

        for (SalesOrderItem item : savedOrder.getItems()) {
            stockServices.recordSale(
                    item.getProduct(),
                    warehouse,
                    item.getQty(),
                    savedOrder.getId(),
                    createdBy
            );
        }
        savedOrder.setStatus(SalesOrderStatus.CONFIRMED);

        return so_repo.save(savedOrder);
    }

    @Transactional
    public SalesOrder cancelOrder(Long id) {
        SalesOrder order = so_repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getStatus() == SalesOrderStatus.DELIVERED || order.getStatus() == SalesOrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot Cancel the Already Delivered or Cancelled Order");
        }

        for (SalesOrderItem item : order.getItems()) {
            stockServices.recordReturn(item.getProduct(), order.getWarehouse(), item.getQty(), order.getId(), order.getCreatedBy());
        }

        order.setStatus(SalesOrderStatus.CANCELLED);

        return so_repo.save(order);
    }

    public List<SalesOrder> getAll () {
        return so_repo.findAll();
    }
}
