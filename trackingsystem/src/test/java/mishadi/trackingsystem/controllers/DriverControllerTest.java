package mishadi.trackingsystem.controllers;

import mishadi.trackingsystem.entities.Driver;
import mishadi.trackingsystem.services.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class DriverControllerTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDrivers_ShouldReturnListOfDrivers() {
        // Arrange
        List<Driver> expectedDrivers = Arrays.asList(
                new Driver(1L, "John Driver", "john.driver@example.com", "1234567890"),
                new Driver(2L, "Jane Driver", "jane.driver@example.com", "0987654321")
        );
        when(driverService.getAllDrivers()).thenReturn(expectedDrivers);

        // Act
        List<Driver> actualDrivers = driverController.getAllDrivers();

        // Assert
        assertEquals(expectedDrivers.size(), actualDrivers.size());
        assertEquals(expectedDrivers, actualDrivers);
    }

    @Test
    void getDriverById_WithValidId_ShouldReturnDriver() {
        // Arrange
        Driver expectedDriver = new Driver(1L, "John Driver", "john.driver@example.com", "1234567890");
        when(driverService.getDriverById(1L)).thenReturn(expectedDriver);

        // Act
        ResponseEntity<Driver> response = driverController.getDriverById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDriver, response.getBody());
    }

    @Test
    void addDriver_WithValidData_ShouldReturnSavedDriver() {
        // Arrange
        Driver driverToAdd = new Driver(null, "John Driver", "john.driver@example.com", "1234567890");
        Driver savedDriver = new Driver(1L, "John Driver", "john.driver@example.com", "1234567890");
        when(driverService.addDriver(any(Driver.class))).thenReturn(savedDriver);

        // Act
        ResponseEntity<Driver> response = driverController.addDriver(driverToAdd);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedDriver, response.getBody());
    }

    @Test
    void updateDriver_WithValidData_ShouldReturnUpdatedDriver() {
        // Arrange
        Long driverId = 1L;
        Driver driverToUpdate = new Driver(driverId, "John Updated", "john.driver@example.com", "1234567890");
        when(driverService.updateDriver(eq(driverId), any(Driver.class))).thenReturn(driverToUpdate);

        // Act
        ResponseEntity<Driver> response = driverController.updateDriver(driverId, driverToUpdate);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(driverToUpdate, response.getBody());
    }

    @Test
    void deleteDriver_WithValidId_ShouldReturnNoContent() {
        // Act
        ResponseEntity<Void> response = driverController.deleteDriver(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }
}