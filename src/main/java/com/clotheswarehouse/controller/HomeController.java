package com.clotheswarehouse.controller;

import com.clotheswarehouse.model.Item;
import com.clotheswarehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private ItemService itemService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        model.addAttribute("totalItems", items.size());
        model.addAttribute("totalValue", items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        return "dashboard";
    }
}
