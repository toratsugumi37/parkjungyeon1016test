package com.kh.product.DAO;

import com.kh.product.FORM.AddForm;
import com.kh.product.FORM.Product;
import com.kh.product.FORM.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{

    private final NamedParameterJdbcTemplate template;

    @Override
    public int addProduct(AddForm addForm) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into product(pid,pname,quantity,price) ");
        sql.append("values(pid_seq.NEXTVAL, :pname , :quantity, :price) ");

        SqlParameterSource param = new BeanPropertySqlParameterSource(addForm);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int addedRow = template.update(sql.toString(),param,keyHolder);

        return addedRow;
    }

    @Override
    public List<Product> viewProduct() {
        StringBuffer sql = new StringBuffer();

        sql.append("select * from product ");
        sql.append("order by pid asc ");


        List<Product> viewdProduct = template.query(sql.toString(),new BeanPropertyRowMapper<>(Product.class));
        log.info("DAOIMPL ViewProduct{}",viewdProduct);

        return viewdProduct;
    }

    @Override
    public Product viewProductDetail(Long pid) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * from product ");
        sql.append("where pid = :pid ");

        Map<String,Long> param = Map.of("pid",pid);
        log.info("조회pid={}",pid);


        Product viewdRow = template.queryForObject(sql.toString(),param,new BeanPropertyRowMapper<>(Product.class));
        log.info("조회ROW={}",viewdRow);

        return viewdRow;
    }

    @Override
    public int deleteProduct(Long pid) {
        String sql = new String();
        sql = "delete from product where pid = :pid";

        int deletedRow = template.update(sql,Map.of("pid",pid));
        return deletedRow;
    }

    @Override
    public int updateProduct(Long pid, Product product) {
        StringBuffer sql = new StringBuffer();

        sql.append("update product ");
        sql.append("set pname = :pname , quantity = :quantity , price = :price ");
        sql.append("where pid = :pid ");

        log.info("sql={}",sql);

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("pname", product.getPname())
                .addValue("quantity", product.getQuantity())
                .addValue("price", product.getPrice())
                .addValue("pid",pid);

        log.info("param={}",param);

        int updatedRow = template.update(sql.toString(),param);

        log.info("updatedRow={} DAO임플성공",updatedRow);

        return updatedRow;
    }
}
