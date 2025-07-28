package com.clotheswarehouse.config;

import com.clotheswarehouse.model.*;
import com.clotheswarehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;
    
    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Load sample users
        if (userRepository.count() == 0) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), "admin@warehouse.com", Role.ADMIN);
            User employee = new User("employee", passwordEncoder.encode("emp123"), "employee@warehouse.com", Role.WAREHOUSE_EMPLOYEE);
            User customer = new User("customer", passwordEncoder.encode("cust123"), "customer@warehouse.com", Role.CUSTOMER);
            
            userRepository.save(admin);
            userRepository.save(employee);
            userRepository.save(customer);
        }
        
        // Load sample warehouse items
        if (itemRepository.count() == 0) {
            itemRepository.save(new Item("Running Shoes", Brand.NIKE, 2023, 129.99, 50));
            itemRepository.save(new Item("Basketball Shoes", Brand.ADIDAS, 2023, 149.99, 30));
            itemRepository.save(new Item("Casual Sneakers", Brand.PUMA, 2022, 89.99, 75));
            itemRepository.save(new Item("Training Shoes", Brand.UNDER_ARMOUR, 2023, 119.99, 40));
            itemRepository.save(new Item("Classic Sneakers", Brand.REEBOK, 2022, 79.99, 60));
        }
        
        // Load distribution centres
        if (distributionCentreRepository.count() == 0) {
            // GTA area distribution centres with realistic coordinates
            DistributionCentre northYork = new DistributionCentre("North York Distribution Centre", 43.7615, -79.4111);
            DistributionCentre mississauga = new DistributionCentre("Mississauga Distribution Centre", 43.5890, -79.6441);
            DistributionCentre scarborough = new DistributionCentre("Scarborough Distribution Centre", 43.7764, -79.2318);
            DistributionCentre markham = new DistributionCentre("Markham Distribution Centre", 43.8561, -79.3370);
            
            distributionCentreRepository.save(northYork);
            distributionCentreRepository.save(mississauga);
            distributionCentreRepository.save(scarborough);
            distributionCentreRepository.save(markham);
            
            // Load sample items in distribution centres
            if (distributionCentreItemRepository.count() == 0) {
                // North York DC items
                distributionCentreItemRepository.save(new DistributionCentreItem("Designer Jacket", Brand.BALENCIAGA, 2023, 899.99, 5, northYork));
                distributionCentreItemRepository.save(new DistributionCentreItem("Luxury Handbag", Brand.GUCCI, 2023, 1299.99, 3, northYork));
                distributionCentreItemRepository.save(new DistributionCentreItem("Sport Jacket", Brand.NIKE, 2023, 199.99, 15, northYork));
                
                // Mississauga DC items
                distributionCentreItemRepository.save(new DistributionCentreItem("Designer Jacket", Brand.BALENCIAGA, 2023, 899.99, 8, mississauga));
                distributionCentreItemRepository.save(new DistributionCentreItem("Casual Shirt", Brand.TOMMY_HILFIGER, 2023, 79.99, 20, mississauga));
                distributionCentreItemRepository.save(new DistributionCentreItem("Dress Shoes", Brand.HUGO_BOSS, 2023, 299.99, 12, mississauga));
                
                // Scarborough DC items
                distributionCentreItemRepository.save(new DistributionCentreItem("Designer Jacket", Brand.BALENCIAGA, 2023, 899.99, 3, scarborough));
                distributionCentreItemRepository.save(new DistributionCentreItem("Polo Shirt", Brand.RALPH_LAUREN, 2023, 89.99, 25, scarborough));
                distributionCentreItemRepository.save(new DistributionCentreItem("Sneakers", Brand.ADIDAS, 2023, 159.99, 18, scarborough));
                
                // Markham DC items
                distributionCentreItemRepository.save(new DistributionCentreItem("Business Suit", Brand.ARMANI, 2023, 1599.99, 4, markham));
                distributionCentreItemRepository.save(new DistributionCentreItem("Casual Pants", Brand.CALVIN_KLEIN, 2023, 119.99, 22, markham));
                distributionCentreItemRepository.save(new DistributionCentreItem("Winter Coat", Brand.PUMA, 2023, 249.99, 10, markham));
            }
        }
    }
}
