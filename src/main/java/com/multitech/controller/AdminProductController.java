package com.multitech.controller;

import com.multitech.dto.ProductDTO;
import com.multitech.model.Product;
import com.multitech.service.CategoryService;
import com.multitech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AdminProductController {
    public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin/products")
    public String displayAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/addProduct")
    public String getAddProduct(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "addProduct";
    }
    @PostMapping("/admin/products/addProduct")
    public String postAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile multipartFile,
                                 @RequestParam("imgName")String imgName) throws IOException {
        Product product=new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(product.getPrice());
        product.setDescription(productDTO.getDescription());
        String productUUID;
        if(!multipartFile.isEmpty()){
            productUUID= multipartFile.getOriginalFilename();
            Path fileNameAndPath= Paths.get(uploadDir,productUUID);
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }else{
            productUUID=imgName;
        }
        product.setImageName(productUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";

    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteById(@PathVariable long id){
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/update/{id}")
    public String updateById(@PathVariable long id,Model model)
    {
        Product product=productService.getProductById(id).get();
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        return "addProduct";
    }
}
