package mishadi.trackingsystem.controllers;

import mishadi.trackingsystem.entities.Customer;
import mishadi.trackingsystem.services.CustomerService;
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

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // Arrange
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1L, "John Doe", "john@example.com", "1234567890"),
                new Customer(2L, "Jane Doe", "jane@example.com", "0987654321")
        );
        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        // Act
        List<Customer> actualCustomers = customerController.getAllCustomers();

        // Assert
        assertEquals(expectedCustomers.size(), actualCustomers.size());
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void getCustomerById_WithValidId_ShouldReturnCustomer() {
        // Arrange
        Customer expectedCustomer = new Customer(1L, "John Doe", "john@example.com", "1234567890");
        when(customerService.getCustomerById(1L)).thenReturn(expectedCustomer);

        // Act
        ResponseEntity<Customer> response = customerController.getCustomerById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCustomer, response.getBody());
    }

    @Test
    void addCustomer_WithValidData_ShouldReturnSavedCustomer() {
        // Arrange
        Customer customerToAdd = new Customer(null, "John Doe", "john@example.com", "1234567890");
        Customer savedCustomer = new Customer(1L, "John Doe", "john@example.com", "1234567890");
        when(customerService.addCustomer(any(Customer.class))).thenReturn(savedCustomer);

        // Act
        ResponseEntity<Customer> response = customerController.addCustomer(customerToAdd);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedCustomer, response.getBody());
    }

    @Test
    void updateCustomer_WithValidData_ShouldReturnUpdatedCustomer() {
        // Arrange
        Long customerId = 1L;
        Customer customerToUpdate = new Customer(customerId, "John Updated", "john@example.com", "1234567890");
        when(customerService.updateCustomer(eq(customerId), any(Customer.class))).thenReturn(customerToUpdate);

        // Act
        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, customerToUpdate);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(customerToUpdate, response.getBody());
    }

    @Test
    void deleteCustomer_WithValidId_ShouldReturnNoContent() {
        // Act
        ResponseEntity<Void> response = customerController.deleteCustomer(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
    }
}