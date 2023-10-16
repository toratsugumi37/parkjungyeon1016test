package com.kh.product.SVC;

import com.kh.product.DAO.ProductDAO;
import com.kh.product.FORM.AddForm;
import com.kh.product.FORM.Product;
import com.kh.product.FORM.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {

    private final ProductDAO productDAO;

    @Override
    public int addProduct(AddForm addForm) {
        return productDAO.addProduct(addForm);
    }

    @Override
    public List<Product> viewProduct() {
        log.info("SVCImpl {}",productDAO.viewProduct());
        return productDAO.viewProduct();
    }

    @Override
    public Product viewProductDetail(Long pid) {
        return productDAO.viewProductDetail(pid);
    }

    @Override
    public int deleteProduct(Long pid) {
        return productDAO.deleteProduct(pid);
    }

    @Override
    public int updateProduct(Long pid, Product product) {
        return productDAO.updateProduct(pid,product);
    }
}
