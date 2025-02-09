package mishadi.trackingsystem.controllers;

import mishadi.trackingsystem.services.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/shipments/status")
    public ResponseEntity<Map<String, Long>> getShipmentsByStatus() {
        return ResponseEntity.ok(analyticsService.getShipmentsByStatus());
    }

    @GetMapping("/drivers/performance")
    public ResponseEntity<Map<String, Object>> getDriverPerformance() {
        return ResponseEntity.ok(analyticsService.getDriverPerformance());
    }

    @GetMapping("/customers/stats")
    public ResponseEntity<Map<String, Object>> getCustomerStats() {
        return ResponseEntity.ok(analyticsService.getCustomerStats());
    }

    @GetMapping("/delivery/stats")
    public ResponseEntity<Map<String, Object>> getDeliveryStats() {
        return ResponseEntity.ok(analyticsService.getDeliveryStats());
    }
} 