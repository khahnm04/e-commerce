drop DATABASE shopco;
USE shopco;

-- USERS
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    date_of_birth DATE,
    gender ENUM('male', 'female', 'other'),
    avatar_url VARCHAR(500),
    password VARCHAR(255) NOT NULL,
    status ENUM('active', 'inactive', 'banned') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
ALTER TABLE users DROP COLUMN code;
ALTER TABLE `shopco`.`users` 
CHANGE COLUMN `phone_number` `phone_number` VARCHAR(255) NOT NULL;
-- ALTER gender column
ALTER TABLE users MODIFY gender ENUM('MALE', 'FEMALE', 'OTHER') DEFAULT 'OTHER';
-- ALTER status column
ALTER TABLE users MODIFY status ENUM('ACTIVE', 'INACTIVE', 'BANNED') DEFAULT 'ACTIVE';


-- PROVINCES / DISTRICTS / WARDS
CREATE TABLE provinces (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE districts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    province_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_districts_provinces_id FOREIGN KEY (province_id) REFERENCES provinces(id)
);

CREATE TABLE wards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    district_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_wards_districts_id FOREIGN KEY (district_id) REFERENCES districts(id)
);

-- ADDRESSES
CREATE TABLE addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ward_id INT NOT NULL,
    street VARCHAR(255) NOT NULL,
    CONSTRAINT fk_addresses_wards_id FOREIGN KEY (ward_id) REFERENCES wards(id)
);

-- ADDRESS_USERS (trung gian user - address)
CREATE TABLE address_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address_id INT NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_users_addresses_users_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_users_addresses_addresses_id FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- BRANDS
CREATE TABLE brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    logo_url VARCHAR(500),
    website VARCHAR(255),
    country VARCHAR(255) DEFAULT 'Vietnam',
    short_description VARCHAR(500),
    description TEXT,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
ALTER TABLE brands
    MODIFY country VARCHAR(255) NOT NULL,
    MODIFY short_description VARCHAR(500) NOT NULL,
    MODIFY description TEXT NOT NULL;
ALTER TABLE brands MODIFY COLUMN status ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE';
UPDATE brands SET status = UPPER(status);
SET SQL_SAFE_UPDATES = 0;

-- CATEGORIES
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    short_description VARCHAR(255),
    description TEXT,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_categories_parent_id FOREIGN KEY (parent_id) REFERENCES categories(id)
);

-- PRODUCTS
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    short_description VARCHAR(255),
    description TEXT,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_products_brands_id FOREIGN KEY (brand_id) REFERENCES brands(id)
);

-- PRODUCTS_CATEGORIES (N-N product - category)
CREATE TABLE products_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_products_categories_products_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_products_categories_categories_id FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- PRODUCTS_VARIANTS
CREATE TABLE products_variants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    sku VARCHAR(100) UNIQUE NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    stock INT DEFAULT 0,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_products_variants_products_id FOREIGN KEY (product_id) REFERENCES products(id)
);

-- PRODUCTS_IMAGES
CREATE TABLE products_images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    product_variant_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    CONSTRAINT fk_products_images_products_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_products_images_products_variants_id FOREIGN KEY (product_variant_id) REFERENCES products_variants(id)
);

-- ATTRIBUTES
CREATE TABLE attributes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL -- Ví dụ: Size, Màu sắc
);

-- ATTRIBUTES_VARIANTS (N-N: variant - attribute)
CREATE TABLE attributes_variants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_variant_id INT NOT NULL,
    attribute_id INT NOT NULL,
    value VARCHAR(100) NOT NULL, -- Ví dụ: Size = M, Color = Red
    CONSTRAINT fk_attributes_variants_products_variants_id FOREIGN KEY (product_variant_id) REFERENCES products_variants(id),
    CONSTRAINT fk_attributes_variants_attributes_id FOREIGN KEY (attribute_id) REFERENCES attributes(id)
);

-- ORDERS
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(15),
    note TEXT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(15,2) NOT NULL,
    receiver_name VARCHAR(150) NOT NULL,
    receiver_phone VARCHAR(20) NOT NULL,
    receiver_address VARCHAR(255) NOT NULL,
    receiver_provinces VARCHAR(100) NOT NULL,
    receiver_district VARCHAR(100) NOT NULL,
    receiver_ward VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_users_id FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ORDERS_DETAILS
CREATE TABLE orders_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_variant_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    CONSTRAINT fk_orders_details_orders_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_orders_details_products_variants_id FOREIGN KEY (product_variant_id) REFERENCES products_variants(id)
);

ALTER TABLE brands MODIFY COLUMN description TEXT;

/*
Hibernate: alter table brands modify column description varchar(255)
Hibernate: alter table categories modify column description varchar(255)
Hibernate: alter table orders modify column note varchar(255)
Hibernate: alter table products modify column description varchar(255)
*/
