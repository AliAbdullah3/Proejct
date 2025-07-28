package com.clotheswarehouse.controller.api;

import com.clotheswarehouse.model.DistributionCentre;
import com.clotheswarehouse.model.DistributionCentreItem;
import com.clotheswarehouse.service.DistributionCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/distribution-centres")
public class DistributionCentreApiController {
    
    @Autowired
    private DistributionCentreService distributionCentreService;
    
    @GetMapping
    public ResponseEntity<List<DistributionCentre>> getAllDistributionCentres() {
        List<DistributionCentre> centres = distributionCentreService.getAllDistributionCentres();
        return ResponseEntity.ok(centres);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DistributionCentre> getDistributionCentreById(@PathVariable Long id) {
        Optional<DistributionCentre> centre = distributionCentreService.getDistributionCentreById(id);
        return centre.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<DistributionCentre> createDistributionCentre(@RequestBody DistributionCentre distributionCentre) {
        DistributionCentre savedCentre = distributionCentreService.saveDistributionCentre(distributionCentre);
        return ResponseEntity.ok(savedCentre);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistributionCentre(@PathVariable Long id) {
        distributionCentreService.deleteDistributionCentre(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/items")
    public ResponseEntity<List<DistributionCentreItem>> getAllItems() {
        List<DistributionCentreItem> items = distributionCentreService.getAllDistributionCentreItems();
        return ResponseEntity.ok(items);
    }
    
    @PostMapping("/{centreId}/items")
    public ResponseEntity<DistributionCentreItem> addItemToDistributionCentre(
            @PathVariable Long centreId, 
            @RequestBody DistributionCentreItem item) {
        
        Optional<DistributionCentre> centre = distributionCentreService.getDistributionCentreById(centreId);
        if (centre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        item.setDistributionCentre(centre.get());
        DistributionCentreItem savedItem = distributionCentreService.saveDistributionCentreItem(item);
        return ResponseEntity.ok(savedItem);
    }
    
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        distributionCentreService.deleteDistributionCentreItem(itemId);
        return ResponseEntity.ok().build();
    }
}
