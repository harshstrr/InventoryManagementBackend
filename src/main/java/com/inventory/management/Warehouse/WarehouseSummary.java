package com.inventory.management.Warehouse;

import com.inventory.management.Warehouse.modal.Warehouse;

public record WarehouseSummary(
        Long id,
        String name,
        String code

) {
    public static WarehouseSummary from(Warehouse w ){
        if(w == null) {
            return null;
        }
        return new WarehouseSummary(w.getId(), w.getName(), w.getCode());

    }
}
