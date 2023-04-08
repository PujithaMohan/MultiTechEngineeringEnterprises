package com.multitech.controller;

import com.multitech.model.Category;
import com.multitech.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AdminCategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin")
    public String getAdminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/category")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "category";
    }

    @GetMapping("/admin/category/addCategory")
    public String getAddCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping("/admin/category/addCategory")
    public String postAddCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "addCategory";
        } else
            return "404";
    }
}
