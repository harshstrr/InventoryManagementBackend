package com.inventory.management.Stocks;

import com.inventory.management.Products.repository.ProductRepository;
import com.inventory.management.Stocks.modal.*;
import com.inventory.management.Products.modal.Product;
import com.inventory.management.Stocks.repository.StockItemRepository;
import com.inventory.management.Stocks.repository.StockMovementRepository;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import com.inventory.management.Warehouse.modal.Warehouse;

import com.inventory.management.Warehouse.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StockServices {

    private final StockItemRepository stockItemRepository;
    private final StockMovementRepository stockMovementRepository;
    private final AppUserRepository appUserRepository;
    private final ProductRepository prod_repo;
    private final WarehouseRepository ware_repo;
    private final AppUserRepository userRepository;

    @Transactional
    public void recordPurchase(Product product, Warehouse warehouse, int qty, Long referenceId , AppUser createdby) {
        StockItem item = stockItemRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseGet(() -> createStockItem(product, warehouse));

        item.setQuantity(item.getQuantity() + qty);       // "the balance" goes up
        stockItemRepository.save(item);

        saveMovement(product, warehouse, MovementType.PURCHASE_IN, "Purchase product from Supplier" , qty, "PURCHASE_ORDER", referenceId , createdby);
        // ^ "the transaction log" gets one new line
    }

   @Transactional
    public void recordSale(Product product, Warehouse warehouse, int qty, Long referenceId, AppUser createdby) {
        StockItem item = stockItemRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseThrow(() -> new RuntimeException(
                        "No stock exists for " + product.getSku() + " at this warehouse"));

        if (item.getQuantity() < qty) {
            throw new RuntimeException(
                    "Only " + item.getQuantity() + " units available for " + product.getSku());
        }

        item.setQuantity(item.getQuantity() - qty);       // "the balance" goes down
        stockItemRepository.save(item);
        saveMovement(product, warehouse, MovementType.SALE_OUT, "Customer Bought the Product" , qty, "SALES_ORDER" ,  referenceId, createdby);
    }

    private StockItem createStockItem(Product product, Warehouse warehouse) {
        StockItem item = new StockItem();
        item.setProduct(product);
        item.setWarehouse(warehouse);
        item.setQuantity(0);
        return item;
    }

    private void saveMovement(Product product, Warehouse warehouse, MovementType type, String note ,
                              int qty, String referenceType, Long referenceId , AppUser createdby) {

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setWarehouse(warehouse);
        movement.setNote(note);
        movement.setType(type);
        movement.setQuantity(qty);
        movement.setReferenceType(referenceType);
        movement.setReferenceId(referenceId);
        movement.setCreatedBy(createdby);
        stockMovementRepository.save(movement);
    }

    @Transactional
    public void recordReturn(Product product, Warehouse warehouse, int qty, Long referenceId, AppUser createdBy) {
        StockItem item = stockItemRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseThrow(() -> new RuntimeException("No stock record to return into"));

        item.setQuantity(item.getQuantity() + qty);   // ← stock goes UP, opposite of recordSale
        stockItemRepository.save(item);

        saveMovement(product, warehouse, MovementType.RETURN, "Stock returned" , qty, "SALES_ORDER", referenceId, createdBy);
    }

    public Void adjustStock(Long productId , Long fromWarehouseId, Long toWarehouseId , int deltaQty , String note , Long createdby) throws Exception {

        Product product = prod_repo.findById(productId)
                .orElseThrow(() -> new Exception("Product Not found"));

        Warehouse toWarehouse = ware_repo.findById(toWarehouseId)
                .orElseThrow(() -> new Exception("Warehouse Not found"));

        Warehouse fromWarehouse = ware_repo.findById(fromWarehouseId)
                .orElseThrow(() -> new Exception("Warehouse Not found"));

        AppUser user = userRepository.findById(createdby)
                .orElseThrow(() -> new Exception("User Not found"));


        StockItem fromItem = stockItemRepository.findByProductIdAndWarehouseId(product.getId() , fromWarehouse.getId())
                .orElseThrow(() -> new Exception("Product not found in warehouse")) ;

        int decreaseQty = fromItem.getQuantity() - deltaQty;
        if(decreaseQty < 0) {
            throw new RuntimeException("Adjustment would result in negative stock");
        }
        fromItem.setQuantity(decreaseQty);
        stockItemRepository.save(fromItem);

        StockItem toItem = stockItemRepository.findByProductIdAndWarehouseId(product.getId() , toWarehouse.getId())
                .orElseGet(() -> createStockItem(product , toWarehouse) );

        int newQty = toItem.getQuantity() + deltaQty;
        if(newQty < 0) {
            throw new RuntimeException("Adjustment would result in negative stock");
        }

        toItem.setQuantity(newQty);
        stockItemRepository.save(toItem);

        saveMovement(product , fromWarehouse , MovementType.ADJUSTMENT , "Shifted Stock to Other Warehouse" , decreaseQty , "MANUAL_ADJUSTMENT", null, user);


        saveMovement(product , toWarehouse , MovementType.ADJUSTMENT ,note , deltaQty , "MANUAL_ADJUSTMENT", null, user);

        return null;
    }


}