package com.kh.product.DAO;

import com.kh.product.FORM.AddForm;
import com.kh.product.FORM.Product;
import com.kh.product.FORM.UpdateForm;

import java.util.List;

public interface ProductDAO {
    int addProduct(AddForm addForm);

    List<Product> viewProduct();

    Product viewProductDetail(Long pid);

    int updateProduct(Long pid,Product product);

    int deleteProduct(Long pid);

}
