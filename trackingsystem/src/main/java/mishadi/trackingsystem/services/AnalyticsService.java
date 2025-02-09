package mishadi.trackingsystem.services;

import mishadi.trackingsystem.entities.ShipmentStatus;
import mishadi.trackingsystem.repositories.CustomerRepository;
import mishadi.trackingsystem.repositories.DriverRepository;
import mishadi.trackingsystem.repositories.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {
    private final ShipmentRepository shipmentRepository;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;

    public AnalyticsService(ShipmentRepository shipmentRepository, 
                           DriverRepository driverRepository, 
                           CustomerRepository customerRepository) {
        this.shipmentRepository = shipmentRepository;
        this.driverRepository = driverRepository;
        this.customerRepository = customerRepository;
    }

    public Map<String, Long> getShipmentsByStatus() {
        Map<String, Long> statusCounts = new HashMap<>();
        for (ShipmentStatus status : ShipmentStatus.values()) {
            long count = shipmentRepository.countByStatus(status);
            statusCounts.put(status.name(), count);
        }
        return statusCounts;
    }

    public Map<String, Object> getDriverPerformance() {
        Map<String, Object> performance = new HashMap<>();
        performance.put("totalDrivers", driverRepository.count());
        performance.put("activeDrivers", shipmentRepository.countDistinctDrivers());
        performance.put("driverStats", shipmentRepository.getDriverDeliveryStats());
        return performance;
    }

    public Map<String, Object> getCustomerStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCustomers", customerRepository.count());
        stats.put("activeCustomers", shipmentRepository.countDistinctCustomers());
        stats.put("topCustomers", shipmentRepository.getTopCustomersByShipmentCount());
        return stats;
    }

    public Map<String, Object> getDeliveryStats() {
        Map<String, Object> stats = new HashMap<>();
        long totalShipments = shipmentRepository.count();
        long deliveredShipments = shipmentRepository.countByStatus(ShipmentStatus.DELIVERED);
        double successRate = totalShipments > 0 ? (double) deliveredShipments / totalShipments * 100 : 0;
        
        stats.put("totalShipments", totalShipments);
        stats.put("deliveredShipments", deliveredShipments);
        stats.put("successRate", String.format("%.2f", successRate));
        stats.put("averageDeliveryTime", shipmentRepository.getAverageDeliveryTime());
        return stats;
    }
} 