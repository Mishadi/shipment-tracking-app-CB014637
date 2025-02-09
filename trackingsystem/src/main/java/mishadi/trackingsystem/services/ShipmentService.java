package mishadi.trackingsystem.services;

import mishadi.trackingsystem.entities.*;
import mishadi.trackingsystem.repositories.CustomerRepository;
import mishadi.trackingsystem.repositories.DriverRepository;
import mishadi.trackingsystem.repositories.ShipmentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;

    public ShipmentService(ShipmentRepository shipmentRepository, CustomerRepository customerRepository, DriverRepository driverRepository) {
        this.shipmentRepository = shipmentRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public Shipment createShipment(Long customerId, Long driverId, String trackingNumber) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Driver driver = driverRepository.findById(driverId)
                .orElse(null); // Driver can be optional

        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(trackingNumber);
        shipment.setCustomer(customer);
        shipment.setDriver(driver);
        shipment.setStatus(ShipmentStatus.PENDING);
        shipment.setUpdatedAt(LocalDateTime.now());

        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipmentStatus(Long id, ShipmentStatus status, String deliveryInstructions) {
        Shipment shipment = getShipmentById(id);
        shipment.setStatus(status);
        shipment.setDeliveryInstructions(deliveryInstructions);
        shipment.setUpdatedAt(LocalDateTime.now());
        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }
}
