package com.clotheswarehouse.repository;

import com.clotheswarehouse.model.Brand;
import com.clotheswarehouse.model.DistributionCentreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionCentreItemRepository extends JpaRepository<DistributionCentreItem, Long> {
    List<DistributionCentreItem> findByNameIgnoreCaseAndBrandAndQuantityGreaterThan(String name, Brand brand, int quantity);
}
