create table if not exists PRODUCT (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(30) NOT NULL,
    measure varchar(30) NOT NULL,
    UNIQUE (name)
);

insert into PRODUCT (name, measure) VALUES ('Капуста', 'кг');
insert into PRODUCT (name, measure) VALUES ('Морковь', 'кг');
insert into PRODUCT (name, measure) VALUES ('Картошка', 'кг');
insert into PRODUCT (name, measure) VALUES ('Вода', 'литр');
insert into PRODUCT (name, measure) VALUES ('Помидоры', 'кг');

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