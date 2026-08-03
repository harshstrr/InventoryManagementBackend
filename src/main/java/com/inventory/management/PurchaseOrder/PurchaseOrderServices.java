package com.inventory.management.PurchaseOrder;

import com.inventory.management.Products.modal.Product;
import com.inventory.management.Products.repository.ProductRepository;
import com.inventory.management.PurchaseOrder.dto.PurchaseOrderRequest;
import com.inventory.management.PurchaseOrder.dto.ReceiveOrderRequest;
import com.inventory.management.PurchaseOrder.modal.PurchaseOrder;
import com.inventory.management.PurchaseOrder.repository.PurchaseOrderRepository;
import com.inventory.management.PurchaseOrderItem.dto.PurchaseOrderItemRequest;
import com.inventory.management.PurchaseOrderItem.dto.ReceiveItemRequest;
import com.inventory.management.PurchaseOrderItem.modal.PurchaseOrderItem;
import com.inventory.management.Stocks.StockServices;
import com.inventory.management.Supplier.modal.Supplier;
import com.inventory.management.Supplier.repository.SupplierRepository;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import com.inventory.management.Warehouse.WarehouseServices;
import com.inventory.management.Warehouse.modal.Warehouse;
import com.inventory.management.Warehouse.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseOrderServices {
    private final PurchaseOrderRepository po_repo;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final StockServices stockServices;
    private final AppUserRepository userRepo;

    private String generatePoNumber() {
        return "PO-" + System.currentTimeMillis();   // simple for now — swap for a proper sequence later
    }

    @Transactional
    public PurchaseOrder createOrder(PurchaseOrderRequest po) {

        Warehouse warehouse = warehouseRepository.findById(po.warehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Supplier supplier = supplierRepository.findById(po.supplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        AppUser user = userRepo.findById(po.createdBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
        PurchaseOrder order = new PurchaseOrder();

        order.setPoNumber(generatePoNumber());
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        order.setOrderDate(po.orderDate());
        order.setExpectedDate(po.expectedDate());
        order.setStatus(PurchaseOrderStatus.ORDERED);
        order.setCreatedBy(user);

        for (PurchaseOrderItemRequest itemReq : po.items()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setPurchaseOrder(order);
            item.setProduct(product);
            item.setQtyOrdered(itemReq.qtyOrdered());
            item.setUnitPrice(itemReq.unitPrice());

            order.getItems().add(item);
        }

        return po_repo.save(order);
    }

    @Transactional
    public PurchaseOrder receiveOrder(Long poId, ReceiveOrderRequest order){
        PurchaseOrder po = po_repo.findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));
        AppUser createdby = userRepo.findById(po.getCreatedBy().getId())
                .orElseThrow(() -> new RuntimeException("User Not found"));
        if(po.getStatus() == PurchaseOrderStatus.CANCELLED || po.getStatus() == PurchaseOrderStatus.RECEIVED) {
            throw new IllegalStateException("Cannont receive an order that is " + po.getStatus());
        }

        for (ReceiveItemRequest receiveItem : order.items()) {
            PurchaseOrderItem poItem = po.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(receiveItem.productId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Item not part of this Purchase order"));
            if(poItem.getQtyOrdered() < poItem.getQtyReceived() + receiveItem.qtyReceived()) {
                throw new RuntimeException("Received Items cannot be more than ordered items");
            }
            poItem.setQtyReceived(poItem.getQtyReceived() + receiveItem.qtyReceived());

            stockServices.recordPurchase(poItem.getProduct() , po.getWarehouse() , receiveItem.qtyReceived() , po.getId() , createdby);

        }

        boolean fullyReceived = po.getItems().stream()
                .allMatch(i -> i.getQtyReceived() >= i.getQtyOrdered());

        po.setStatus(fullyReceived ? PurchaseOrderStatus.RECEIVED : PurchaseOrderStatus.PARTIALLY_RECEIVED);

        return po_repo.save(po);
    }

    @Transactional
    public PurchaseOrder cancelOrder(Long poId) {
        PurchaseOrder po = po_repo.findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        if(po.getStatus() == PurchaseOrderStatus.PARTIALLY_RECEIVED || po.getStatus() == PurchaseOrderStatus.RECEIVED) {
            throw new RuntimeException("Can't cancel the already received Item");
        }

        po.setStatus(PurchaseOrderStatus.CANCELLED);
        return po_repo.save(po);
    }

    public List<PurchaseOrder> getAll () {
        return po_repo.findAll();
    }

}
