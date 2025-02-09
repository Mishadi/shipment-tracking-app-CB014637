package mishadi.trackingsystem.controllers;

import mishadi.trackingsystem.entities.Customer;
import mishadi.trackingsystem.entities.Driver;
import mishadi.trackingsystem.entities.Shipment;
import mishadi.trackingsystem.entities.ShipmentStatus;
import mishadi.trackingsystem.services.ShipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;

    @InjectMocks
    private ShipmentController shipmentController;

    private Customer testCustomer;
    private Driver testDriver;
    private Shipment testShipment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCustomer = new Customer(1L, "John Doe", "john@example.com", "1234567890");
        testDriver = new Driver(1L, "John Driver", "driver@example.com", "0987654321");

        testShipment = new Shipment();
        testShipment.setId(1L);
        testShipment.setTrackingNumber("TRACK123");
        testShipment.setCustomer(testCustomer);
        testShipment.setDriver(testDriver);
        testShipment.setStatus(ShipmentStatus.PENDING);
        testShipment.setCreatedAt(LocalDateTime.now());
        testShipment.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getAllShipments_ShouldReturnListOfShipments() {
        // Arrange
        List<Shipment> expectedShipments = Arrays.asList(testShipment);
        when(shipmentService.getAllShipments()).thenReturn(expectedShipments);

        // Act
        List<Shipment> actualShipments = shipmentController.getAllShipments();

        // Assert
        assertEquals(expectedShipments.size(), actualShipments.size());
        assertEquals(expectedShipments, actualShipments);
    }

    @Test
    void getShipmentById_WithValidId_ShouldReturnShipment() {
        // Arrange
        when(shipmentService.getShipmentById(1L)).thenReturn(testShipment);

        // Act
        ResponseEntity<Shipment> response = shipmentController.getShipmentById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testShipment, response.getBody());
    }

    @Test
    void createShipment_WithValidData_ShouldReturnCreatedShipment() {
        // Arrange
        when(shipmentService.createShipment(eq(1L), eq(1L), eq("TRACK123")))
                .thenReturn(testShipment);

        // Act
        ResponseEntity<Shipment> response = shipmentController.createShipment(1L, 1L, "TRACK123");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testShipment, response.getBody());
    }

    @Test
    void updateShipmentStatus_WithValidData_ShouldReturnUpdatedShipment() {
        // Arrange
        ShipmentController.ShipmentStatusUpdateRequest updateRequest = new ShipmentController.ShipmentStatusUpdateRequest();
        updateRequest.setStatus(ShipmentStatus.IN_TRANSIT);
        updateRequest.setDeliveryInstructions("Leave at front door");

        testShipment.setStatus(ShipmentStatus.IN_TRANSIT);
        testShipment.setDeliveryInstructions("Leave at front door");

        when(shipmentService.updateShipmentStatus(eq(1L), any(ShipmentStatus.class), any(String.class)))
                .thenReturn(testShipment);

        // Act
        ResponseEntity<Shipment> response = shipmentController.updateShipmentStatus(1L, updateRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testShipment, response.getBody());
        assertEquals(ShipmentStatus.IN_TRANSIT, response.getBody().getStatus());
        assertEquals("Leave at front door", response.getBody().getDeliveryInstructions());
    }

    @Test
    void deleteShipment_WithValidId_ShouldReturnNoContent() {
        // Act
        ResponseEntity<Void> response = shipmentController.deleteShipment(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }
}