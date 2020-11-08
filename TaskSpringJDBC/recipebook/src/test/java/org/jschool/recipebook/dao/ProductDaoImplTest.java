package org.jschool.recipebook.dao;

import org.jschool.recipebook.config.DataConfiguration;
import org.jschool.recipebook.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;


public class ProductDaoImplTest {
    private final
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(DataConfiguration.class);
    public DataSource dataSource = context.getBean(DataSource.class);
    public ProductDao productDao = context.getBean(ProductDao.class);
    public JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

    @Before
    public void setUp() throws Exception {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/createTables.sql"));
    }

    @After
    public void tearDown() throws Exception {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/dropTables.sql"));
    }

    @Test
    public void createProductTest() {
        Product product = new Product();
        product.setName("Укроп");
        product.setMeasure("г");
        productDao.createProduct(product);
        System.out.println("Добавляем " + product);
        List<Product> productsFromDB = jdbcTemplate.query("select * from product where name='Укроп'", (resultSet, i) -> {
            Product newProduct = new Product();
            newProduct.setId(resultSet.getInt(1));
            newProduct.setName(resultSet.getString(2));
            newProduct.setMeasure(resultSet.getString(3));
            return newProduct;
        });
        System.out.println("Запрос к БД: select * from product where name='Укроп'");
        System.out.println("Получено записей " + productsFromDB.size() + ":");
        productsFromDB.forEach(System.out::println);
        Assert.assertEquals("Количество записей в таблице не равно 1",productsFromDB.size(),1);
        Assert.assertEquals("Запись в таблице не верна", productsFromDB.get(0), product);
    }

    @Test
    public void findProductByNameTest() {
        System.out.println("Ищем продукт Капуста по имени");
        Product product = productDao.findProductByName("Капуста");
        System.out.println(product);
        Product actualProduct = new Product(1, "Капуста",  "кг");
        Assert.assertEquals(product, actualProduct);
    }

    @Test
    public void getAllProductsTest() {
        System.out.println("Получаем все продукты:");
        List<Product> allProducts = productDao.getAllProducts();
        allProducts.forEach(System.out::println);
        List<Product> actualListProducts = new ArrayList<>();
        actualListProducts.add(new Product(1,"Капуста","кг"));
        actualListProducts.add(new Product(2, "Морковь", "кг"));
        actualListProducts.add(new Product(3, "Картошка", "кг"));
        actualListProducts.add(new Product(4, "Вода", "литр"));
        actualListProducts.add(new Product(5, "Помидоры", "кг"));
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(allProducts.get(i), actualListProducts.get(i));
        }
    }

    @Test
    public void getProductById() {
        System.out.println("Ищем продукт с id = 2");
        Product product = productDao.getProductById(2);
        System.out.println(product);
        Product actualProduct = new Product(2, "Морковь",  "кг");
        Assert.assertEquals(product, actualProduct);
    }
}