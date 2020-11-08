package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private final DataSource dataSource;
    private final SimpleJdbcInsert insertProduct;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Product> productRowMapper;

    public ProductDaoImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dataSource = dataSource;
        this.insertProduct = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.productRowMapper = (resultSet, i) -> {
            Product product = new Product();
            product.setId(resultSet.getInt(1));
            product.setName(resultSet.getString(2));
            product.setMeasure(resultSet.getString(3));
            return product;
        };
    }

    @Override
    public void createProduct(Product product) {
        Number returnId = insertProduct.executeAndReturnKey(new BeanPropertySqlParameterSource(product));
        product.setId((int)returnId);

    }

    @Override
    public Product findProductByName(String productName) {
        List<Product> products = namedParameterJdbcTemplate
            .query("select * from PRODUCT where name=:name",
                    new MapSqlParameterSource("name", productName), productRowMapper);
        if (products.isEmpty()) {
            System.out.println("No product "+ productName);
        }
        return products.get(0);
    }

    @Override
    public List<Product> getAllProducts() {
        MappingSqlQuery<Product> mappingSqlQuery = new MappingSqlQuery<Product>(dataSource, "select * from product;") {
            @Override
            protected Product mapRow(ResultSet resultSet, int i) throws SQLException {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setMeasure(resultSet.getString(3));
                return product;
            }
        };
        return mappingSqlQuery.execute();
    }

    @Override
    public Product getProductById(int productId) {
        List<Product> products = namedParameterJdbcTemplate
                .query("select * from PRODUCT where id=:id",
                        new MapSqlParameterSource("id", productId), productRowMapper);
        if (products.isEmpty()) {
            System.out.println("No product with id = "+ productId);
        }
        return products.get(0);
    }
}
