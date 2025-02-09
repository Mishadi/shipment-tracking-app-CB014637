package mishadi.trackingsystem.controllers;

import mishadi.trackingsystem.services.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class AnalyticsControllerTest {

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private AnalyticsController analyticsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getShipmentsByStatus_ShouldReturnStatusMap() {
        // Arrange
        Map<String, Long> expectedStats = new HashMap<>();
        expectedStats.put("PENDING", 5L);
        expectedStats.put("DELIVERED", 3L);
        when(analyticsService.getShipmentsByStatus()).thenReturn(expectedStats);

        // Act
        ResponseEntity<Map<String, Long>> response = analyticsController.getShipmentsByStatus();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedStats, response.getBody());
    }

    @Test
    void getDriverPerformance_ShouldReturnPerformanceStats() {
        // Arrange
        Map<String, Object> expectedStats = new HashMap<>();
        expectedStats.put("totalDrivers", 10L);
        expectedStats.put("activeDrivers", 8L);
        when(analyticsService.getDriverPerformance()).thenReturn(expectedStats);

        // Act
        ResponseEntity<Map<String, Object>> response = analyticsController.getDriverPerformance();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedStats, response.getBody());
    }

    @Test
    void getCustomerStats_ShouldReturnCustomerStats() {
        // Arrange
        Map<String, Object> expectedStats = new HashMap<>();
        expectedStats.put("totalCustomers", 15L);
        expectedStats.put("activeCustomers", 12L);
        when(analyticsService.getCustomerStats()).thenReturn(expectedStats);

        // Act
        ResponseEntity<Map<String, Object>> response = analyticsController.getCustomerStats();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedStats, response.getBody());
    }

    @Test
    void getDeliveryStats_ShouldReturnDeliveryStats() {
        // Arrange
        Map<String, Object> expectedStats = new HashMap<>();
        expectedStats.put("totalDeliveries", 100L);
        expectedStats.put("successRate", "95.5");
        when(analyticsService.getDeliveryStats()).thenReturn(expectedStats);

        // Act
        ResponseEntity<Map<String, Object>> response = analyticsController.getDeliveryStats();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedStats, response.getBody());
    }
}