-- ============================================================
-- Inventory Management System — Initial Schema
-- Flyway migration: V1__create_initial_schema.sql
-- Target: MySQL 8.x
-- ============================================================

CREATE TABLE category (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(120) NOT NULL,
    parent_id   BIGINT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku                 VARCHAR(64) NOT NULL UNIQUE,
    name                VARCHAR(200) NOT NULL,
    description         TEXT NULL,
    category_id         BIGINT NULL,
    unit                VARCHAR(20) NOT NULL DEFAULT 'PCS',
    cost_price          DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    selling_price       DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    reorder_threshold   INT NOT NULL DEFAULT 0,
    reorder_qty         INT NOT NULL DEFAULT 0,
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id),
    INDEX idx_product_sku (sku),
    INDEX idx_product_category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE warehouse (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(120) NOT NULL,
    code        VARCHAR(20) NOT NULL UNIQUE,
    address     VARCHAR(255) NULL,
    city        VARCHAR(100) NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE app_user (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(120) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stock_item (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id    BIGINT NOT NULL,
    warehouse_id  BIGINT NOT NULL,
    quantity      INT NOT NULL DEFAULT 0,
    version       BIGINT NOT NULL DEFAULT 0,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_stockitem_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_stockitem_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    CONSTRAINT uq_stockitem_product_warehouse UNIQUE (product_id, warehouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stock_movement (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id    BIGINT NOT NULL,
    warehouse_id  BIGINT NOT NULL,
    type          ENUM('PURCHASE_IN','SALE_OUT','TRANSFER_IN','TRANSFER_OUT','ADJUSTMENT','RETURN') NOT NULL,
    quantity      INT NOT NULL,
    reference_type VARCHAR(50) NULL,
    reference_id  BIGINT NULL,
    notes         VARCHAR(255) NULL,
    created_by    BIGINT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movement_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_movement_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    CONSTRAINT fk_movement_user FOREIGN KEY (created_by) REFERENCES app_user(id),
    INDEX idx_movement_product_warehouse (product_id, warehouse_id),
    INDEX idx_movement_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE supplier (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    contact_person  VARCHAR(120) NULL,
    email           VARCHAR(150) NULL,
    phone           VARCHAR(30) NULL,
    address         VARCHAR(255) NULL,
    gst_no          VARCHAR(30) NULL,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE purchase_order (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    po_number       VARCHAR(50) NOT NULL UNIQUE,
    supplier_id     BIGINT NOT NULL,
    warehouse_id    BIGINT NOT NULL,
    status          ENUM('DRAFT','ORDERED','PARTIALLY_RECEIVED','RECEIVED','CANCELLED') NOT NULL DEFAULT 'DRAFT',
    order_date      DATE NOT NULL,
    expected_date   DATE NULL,
    created_by      BIGINT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_po_supplier FOREIGN KEY (supplier_id) REFERENCES supplier(id),
    CONSTRAINT fk_po_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    CONSTRAINT fk_po_user FOREIGN KEY (created_by) REFERENCES app_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE purchase_order_item (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    po_id           BIGINT NOT NULL,
    product_id      BIGINT NOT NULL,
    qty_ordered     INT NOT NULL,
    qty_received    INT NOT NULL DEFAULT 0,
    unit_price      DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_poitem_po FOREIGN KEY (po_id) REFERENCES purchase_order(id) ON DELETE CASCADE,
    CONSTRAINT fk_poitem_product FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sales_order (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number    VARCHAR(50) NOT NULL UNIQUE,
    customer_name   VARCHAR(150) NULL,
    customer_contact VARCHAR(100) NULL,
    warehouse_id    BIGINT NOT NULL,
    status          ENUM('PENDING','CONFIRMED','SHIPPED','DELIVERED','CANCELLED') NOT NULL DEFAULT 'PENDING',
    order_date      DATE NOT NULL,
    created_by      BIGINT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_so_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    CONSTRAINT fk_so_user FOREIGN KEY (created_by) REFERENCES app_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sales_order_item (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id        BIGINT NOT NULL,
    product_id      BIGINT NOT NULL,
    qty             INT NOT NULL,
    unit_price      DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_soitem_order FOREIGN KEY (order_id) REFERENCES sales_order(id) ON DELETE CASCADE,
    CONSTRAINT fk_soitem_product FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE stock_transfer (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    transfer_number     VARCHAR(50) NOT NULL UNIQUE,
    product_id          BIGINT NOT NULL,
    from_warehouse_id   BIGINT NOT NULL,
    to_warehouse_id     BIGINT NOT NULL,
    qty                 INT NOT NULL,
    status              ENUM('REQUESTED','IN_TRANSIT','COMPLETED','CANCELLED') NOT NULL DEFAULT 'REQUESTED',
    requested_by        BIGINT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at        TIMESTAMP NULL,
    CONSTRAINT fk_transfer_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_transfer_from_wh FOREIGN KEY (from_warehouse_id) REFERENCES warehouse(id),
    CONSTRAINT fk_transfer_to_wh FOREIGN KEY (to_warehouse_id) REFERENCES warehouse(id),
    CONSTRAINT fk_transfer_user FOREIGN KEY (requested_by) REFERENCES app_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE low_stock_alert (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id      BIGINT NOT NULL,
    warehouse_id    BIGINT NOT NULL,
    current_qty     INT NOT NULL,
    threshold       INT NOT NULL,
    triggered_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at     TIMESTAMP NULL,
    is_resolved     BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_alert_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_alert_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    INDEX idx_alert_unresolved (is_resolved)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
