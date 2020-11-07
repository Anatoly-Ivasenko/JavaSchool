package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProduct;
    private final SimpleJdbcCall jdbcCall;

    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.insertProduct = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("getProduct");
    }

    @Override
    public void createProduct(Product product) {
        Number returnId = insertProduct.executeAndReturnKey(new BeanPropertySqlParameterSource(product));
        product.setId((int)returnId);
    }

    @Override
    public Product findProductByName(String productName) {
        Product product = new Product();
        Map<String, Object> resultCall =
                jdbcCall.execute(new MapSqlParameterSource().addValue("name", productName));
        product.setId((int) resultCall.get("id"));
        product.setName((String) resultCall.get("name"));
        product.setMeasure((String) resultCall.get("measure"));
        return product;
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
        Product product = new Product();
        Map<String, Object> resultCall =
                jdbcCall.execute(new MapSqlParameterSource().addValue("id", productId));
        product.setId((int) resultCall.get("id"));
        product.setName((String) resultCall.get("name"));
        product.setMeasure((String) resultCall.get("measure"));
        return product;
    }
}
