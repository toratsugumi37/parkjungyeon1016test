package com.kh.product.SVC;

import com.kh.product.FORM.AddForm;
import com.kh.product.FORM.Product;
import com.kh.product.FORM.UpdateForm;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
    int addProduct(AddForm addForm);

    List<Product> viewProduct();

    Product viewProductDetail(Long pid);

    int updateProduct(Long pid, Product product);

    int deleteProduct(Long pid);
}
