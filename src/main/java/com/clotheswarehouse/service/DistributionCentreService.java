package com.clotheswarehouse.service;

import com.clotheswarehouse.dto.ItemRequestDto;
import com.clotheswarehouse.model.DistributionCentre;
import com.clotheswarehouse.model.DistributionCentreItem;
import com.clotheswarehouse.model.Item;
import com.clotheswarehouse.repository.DistributionCentreItemRepository;
import com.clotheswarehouse.repository.DistributionCentreRepository;
import com.clotheswarehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DistributionCentreService {
    
    // Warehouse location (CN Tower area, Toronto)
    private static final double WAREHOUSE_LATITUDE = 43.6426;
    private static final double WAREHOUSE_LONGITUDE = -79.3871;
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;
    
    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    public List<DistributionCentre> getAllDistributionCentres() {
        return distributionCentreRepository.findAll();
    }
    
    public Optional<DistributionCentre> getDistributionCentreById(Long id) {
        return distributionCentreRepository.findById(id);
    }
    
    public DistributionCentre saveDistributionCentre(DistributionCentre distributionCentre) {
        return distributionCentreRepository.save(distributionCentre);
    }
    
    public void deleteDistributionCentre(Long id) {
        distributionCentreRepository.deleteById(id);
    }
    
    public List<DistributionCentreItem> getAllDistributionCentreItems() {
        return distributionCentreItemRepository.findAll();
    }
    
    public DistributionCentreItem saveDistributionCentreItem(DistributionCentreItem item) {
        return distributionCentreItemRepository.save(item);
    }
    
    public void deleteDistributionCentreItem(Long id) {
        distributionCentreItemRepository.deleteById(id);
    }
    
    @Transactional
    public String requestItemFromDistributionCentre(ItemRequestDto itemRequest) {
        try {
            // Find all distribution centres that have the requested item with quantity > 0
            List<DistributionCentreItem> availableItems = distributionCentreItemRepository
                    .findByNameIgnoreCaseAndBrandAndQuantityGreaterThan(
                            itemRequest.getName(), 
                            itemRequest.getBrand(), 
                            0
                    );
            
            if (availableItems.isEmpty()) {
                return "ERROR: No distribution centres have the requested item '" + 
                       itemRequest.getName() + "' (" + itemRequest.getBrand().getDisplayName() + ") in stock.";
            }
            
            // Find the closest distribution centre
            DistributionCentreItem closestItem = null;
            double minDistance = Double.MAX_VALUE;
            String closestCentreName = "";
            
            for (DistributionCentreItem item : availableItems) {
                DistributionCentre centre = item.getDistributionCentre();
                double distance = calculateDistance(
                    WAREHOUSE_LATITUDE, WAREHOUSE_LONGITUDE,
                    centre.getLatitude(), centre.getLongitude()
                );
                
                if (distance < minDistance) {
                    minDistance = distance;
                    closestItem = item;
                    closestCentreName = centre.getName();
                }
            }
            
            if (closestItem == null) {
                return "ERROR: Unable to find suitable distribution centre.";
            }
            
            // Check if item already exists in warehouse
            List<Item> existingWarehouseItems = itemRepository.findByNameIgnoreCaseAndBrand(
                itemRequest.getName(), itemRequest.getBrand()
            );
            
            if (!existingWarehouseItems.isEmpty()) {
                // Update existing warehouse item quantity
                Item existingItem = existingWarehouseItems.get(0);
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                itemRepository.save(existingItem);
            } else {
                // Create new warehouse item
                Item newWarehouseItem = new Item(
                    closestItem.getName(),
                    closestItem.getBrand(),
                    closestItem.getYearOfCreation(),
                    closestItem.getPrice(),
                    1 // Request 1 item
                );
                itemRepository.save(newWarehouseItem);
            }
            
            // Reduce quantity in distribution centre
            closestItem.setQuantity(closestItem.getQuantity() - 1);
            distributionCentreItemRepository.save(closestItem);
            
            return String.format("SUCCESS: Item '%s' (%s) successfully transferred from %s (%.1f km away) to warehouse. " +
                    "Warehouse stock updated. Distribution centre stock: %d remaining.",
                    itemRequest.getName(),
                    itemRequest.getBrand().getDisplayName(),
                    closestCentreName,
                    minDistance,
                    closestItem.getQuantity()
            );
            
        } catch (Exception e) {
            return "ERROR: Failed to process item request - " + e.getMessage();
        }
    }
    
    // Calculate distance between two points using Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // Distance in km
        
        return distance;
    }
}
