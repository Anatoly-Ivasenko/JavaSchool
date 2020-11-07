create table if not exists PRODUCT (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(30) NOT NULL,
    measure varchar(30) NOT NULL,
    UNIQUE (name)
);

create table if not exists RECIPE (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL
);

create table if not exists INGREDIENT (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    recipe_id INT NOT NULL,
    product_id INT NOT NULL,
    value DOUBLE NOT NULL
);

create table if not exists PHOTO (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    recipe_id INT NOT NULL,
    photo BLOB NOT NULL
)