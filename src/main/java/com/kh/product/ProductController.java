package com.kh.product;

import com.kh.product.FORM.AddForm;
import com.kh.product.FORM.Product;
import com.kh.product.FORM.UpdateForm;
import com.kh.product.FORM.ViewForm;
import com.kh.product.SVC.ProductSVC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductSVC productSVC;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("redirect:/product/view");
        return mav;
    }

    @GetMapping("/product/add")
    public ModelAndView addForm(AddForm addForm){
        ModelAndView mav = new ModelAndView("product/addProduct");
        mav.addObject(addForm);
        return mav;
    }

    @PostMapping("/product/add")
    public ModelAndView addProduct(@Valid AddForm addForm, BindingResult bindingResult, ModelAndView mav){


        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("pname","pname","");
            log.info("bindingResult={}",bindingResult);
            mav.setViewName("product/addProduct");
            return mav;
        }
        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("quantity","quantity","음수비허용");
            log.info("bindingResult={}",bindingResult);
            mav.setViewName("product/addProduct");
            return mav;
        }
        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("price","price","가격은 1000원이상");
            log.info("{}",bindingResult);
            mav.setViewName("product/addProduct");
            return mav;
        }

        if((addForm.getQuantity() * addForm.getPrice() ) > 100000000){
            bindingResult.reject("global","총금액(수량*가격)1억원이상 불가능");
            log.info("{}",bindingResult);
            mav.setViewName("product/addProduct");
            return mav;
        }


        addForm.setPname(addForm.getPname());
        addForm.setQuantity(addForm.getQuantity());
        addForm.setPrice(addForm.getPrice());

        productSVC.addProduct(addForm);
        mav.setViewName("redirect:/product/view");
        return mav;
    }

    @GetMapping("/product/view")
    public ModelAndView viewProduct(){
        ModelAndView mav = new ModelAndView("product/allProduct");

        List<Product> productList = productSVC.viewProduct();
        List<ViewForm> viewList = new ArrayList<>();
        for(Product product :productList){
            ViewForm viewForm = new ViewForm();
            viewForm.setPid(product.getPid());
            viewForm.setPname(product.getPname());
            viewForm.setQuantity(product.getQuantity());
            viewForm.setPrice(product.getPrice());
            viewList.add(viewForm);
        }
        log.info("cont 1 {}",productSVC.viewProduct());
        mav.addObject("viewForm",viewList);
        return mav;
    }

    @GetMapping("/product/viewDetail/{pid}")
    public ModelAndView viewProductDetail(@PathVariable Long pid){
        ModelAndView mav = new ModelAndView();
        Product viewdProductRow = productSVC.viewProductDetail(pid);
        log.info("viewdProductRow controller = {}",viewdProductRow);
        mav.addObject("DetailForm",viewdProductRow);
        mav.setViewName("product/viewProduct");
        return mav;
    }



    @GetMapping("/product/delete/{pid}")
    public ModelAndView deleteForm(@PathVariable Long pid){
        ModelAndView mav = new ModelAndView();
        int deletedRow = productSVC.deleteProduct(pid);

        mav.setViewName("redirect:/product/view");
        return mav;
    }

    @GetMapping("/product/update/{pid}")
    public ModelAndView updateForm(@PathVariable Long pid,UpdateForm updateForm){
        ModelAndView mav = new ModelAndView("product/updateProduct");
        Product product = productSVC.viewProductDetail(pid);
        updateForm.setPid(product.getPid());
        updateForm.setPname(product.getPname());
        updateForm.setQuantity(product.getQuantity());
        updateForm.setPrice(product.getPrice());
        log.info("update호출={}",updateForm);

        mav.addObject("pid",pid);
        mav.addObject("updateForm",updateForm);
        return mav;
    }

    @PostMapping("/product/update/{pid}")
    public String updateProduct(@PathVariable Long pid, @ModelAttribute @Valid UpdateForm updateForm,BindingResult bindingResult,Model model){
        log.info("updatePID={}",pid);
        log.info("updateForm={}",updateForm);

        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("pname","pname","");
            log.info("bindingResult={}",bindingResult);
            return "product/updateProduct";
        }
        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("quantity","quantity","음수비허용");
            log.info("bindingResult={}",bindingResult);
            return "product/updateProduct";
        }
        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("price","price","가격은 1000원이상");
            log.info("{}",bindingResult);
            return "product/updateProduct";
        }

        Product product = new Product();
        product.setPname(updateForm.getPname());
        product.setQuantity(updateForm.getQuantity());
        product.setPrice(updateForm.getPrice());
        product.setPid(pid);

        int updatedRow = productSVC.updateProduct(pid,product);

        log.info("updatedRow={}",updatedRow);
        model.addAttribute("updateForm",updateForm);

        return "redirect:/product/viewDetail/" + pid;
    }
}
