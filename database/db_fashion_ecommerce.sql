drop database db_ecommerce;
use db_ecommerce;

-- Tạo bảng users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20) UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(255) NOT NULL,
    avatar TEXT,
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'UNKNOWN') DEFAULT 'UNKNOWN',
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    last_login_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng Role
CREATE TABLE roles (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng Role_Users
CREATE TABLE role_users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_role_users__users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_users__roles FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE (user_id, role_id)
);

-- Bảng Permission
CREATE TABLE permissions (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng Permission_Roles
CREATE TABLE permission_roles (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_permission_roles__roles FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_permission_roles__permissions FOREIGN KEY (permission_id) REFERENCES permissions(id),
    UNIQUE (role_id, permission_id)
);

-- Bảng Address
CREATE TABLE addresses (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    province VARCHAR(255) NOT NULL,
    ward VARCHAR(255) NOT NULL,
    home_address VARCHAR(255) NOT NULL,
    reminiscent_name VARCHAR(255),
    address_type ENUM('HOME', 'WORK') DEFAULT 'HOME',
    is_default BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_addresses__users FOREIGN KEY (user_id) REFERENCES users(id)
);

-- bảng Category
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    image TEXT,
    parent_id BIGINT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_categories__parent FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- bảng Brand
CREATE TABLE brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    logo TEXT,
    country VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- bảng Product
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    price BIGINT NOT NULL,
    old_price BIGINT NOT NULL,
    image TEXT NOT NULL,
    description TEXT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'OUT_OF_STOCK', 'PENDING', 'DISCONTINUED') DEFAULT 'ACTIVE',
    brand_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_products__brands FOREIGN KEY (brand_id) REFERENCES brands(id)
);

-- Bảng Category_Products
CREATE TABLE category_products (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_category_products__products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_category_products__categories FOREIGN KEY (category_id) REFERENCES categories(id),
    UNIQUE (product_id, category_id)
);

-- Bảng Attribute
CREATE TABLE attributes (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng Product_Attribute_Values
CREATE TABLE product_attribute_values (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_attribute_values__products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_product_attribute_values__attributes FOREIGN KEY (attribute_id) REFERENCES attributes(id),
    UNIQUE (product_id, attribute_id, value)
);

-- Bảng Product_Variants
CREATE TABLE product_variants (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    sku VARCHAR(255) NOT NULL UNIQUE,
    price BIGINT NOT NULL,
    old_price BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_variants__products FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Bảng Variants
CREATE TABLE variants (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng Variant_Values
CREATE TABLE variant_values (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    variant_id BIGINT NOT NULL,
    value VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_variant_values__variants FOREIGN KEY (variant_id) REFERENCES variants(id),
    UNIQUE (variant_id, value)
);

-- Bảng Product_Variant_Values
CREATE TABLE product_variant_values (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_variant_id BIGINT NOT NULL,
    variant_value_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_variants__product_variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    CONSTRAINT fk_product_variants__variant_values FOREIGN KEY (variant_value_id) REFERENCES variant_values(id),
    UNIQUE (product_variant_id, variant_value_id)
);

-- Bảng Order
CREATE TABLE orders (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_code VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    total_amount BIGINT NOT NULL,
    note TEXT,
    order_status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
	shipping_name VARCHAR(255) NOT NULL,
    shipping_phone VARCHAR(20) NOT NULL,
    shipping_province VARCHAR(255) NOT NULL,
    shipping_ward VARCHAR(255) NOT NULL,
    shipping_home_address VARCHAR(255) NOT NULL,
    required_date DATE, -- Ngày dự kiến giao
	shipped_date DATE, -- Thời gian hoàn thànhorder_details
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_orders__users FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Bảng Order_Details
CREATE TABLE order_details (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	order_id BIGINT NOT NULL,
	product_variant_id BIGINT NOT NULL,
    unit_price BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_order_details__orders FOREIGN KEY (order_id) REFERENCES orders(id),
	CONSTRAINT fk_order_details__product_variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    UNIQUE (order_id, product_variant_id)
);

-- Bảng Product_Faqs
CREATE TABLE product_faqs (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    question VARCHAR(500),
    answer TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_faqs__products FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Bảng Feedback
CREATE TABLE feedbacks (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_detail_id BIGINT NOT NULL UNIQUE,
    rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    content TEXT NOT NULL,
    images JSON,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_feedbacks__order_details FOREIGN KEY (order_detail_id) REFERENCES order_details(id)
);

-- Bảng Product_Images
CREATE TABLE product_images (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
	CONSTRAINT fk_product_images__products FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Bảng News
CREATE TABLE news (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(500),
    image TEXT,
    content TEXT,
    status ENUM('PENDING', 'ACTIVE', 'INACTIVE') DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng News_Detail
CREATE TABLE news_details (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    news_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
	CONSTRAINT fk_news_details__products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_news_details__news FOREIGN KEY (news_id) REFERENCES news(id),
    UNIQUE (product_id, news_id)
);

-- Bảng Banner
CREATE TABLE banners (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    image TEXT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

-- Bảng Banner_Detail
CREATE TABLE banner_details (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    banner_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
	CONSTRAINT fk_banner_details__products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_banner_details__banners FOREIGN KEY (banner_id) REFERENCES banners(id),
    UNIQUE (product_id, banner_id)
);

CREATE TABLE warehouses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,      -- Tên kho
    location VARCHAR(255) NOT NULL,           -- Vị trí kho
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME
);

CREATE TABLE inventories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_variant_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_inventory__product_variants FOREIGN KEY (product_variant_id) REFERENCES product_variants(id),
    CONSTRAINT fk_inventory__warehouses FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
    UNIQUE (product_variant_id, warehouse_id)  -- 1 variant 1 kho 1 row
);

CREATE TABLE stock_movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_id BIGINT NOT NULL,          
    type ENUM('IN','OUT','ADJUSTMENT') NOT NULL,  
    quantity BIGINT NOT NULL,
    note TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_stock_movements__inventories FOREIGN KEY (inventory_id) REFERENCES inventories(id)
);

CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,                  -- Liên kết đơn hàng
    payment_method ENUM('CASH','CARD','TRANSFER','WALLET') NOT NULL,
	payment_status ENUM('UNPAID','PAID','REFUNDED') DEFAULT 'UNPAID',
    amount BIGINT NOT NULL,                    -- Số tiền thanh toán
    transaction_code VARCHAR(100),             -- Mã giao dịch từ ngân hàng/cổng thanh toán
    paid_at DATETIME,                          -- Thời gian thanh toán
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_payments__orders FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE product_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted_at DATETIME,
    CONSTRAINT fk_product_questions__products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_product_questions__users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_product_questions__parents FOREIGN KEY (parent_id) REFERENCES product_questions(id)
);




