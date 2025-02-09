package mishadi.trackingsystem.controllers;

import mishadi.trackingsystem.entities.Shipment;
import mishadi.trackingsystem.entities.ShipmentStatus;
import mishadi.trackingsystem.services.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(
            @RequestParam Long customerId,
            @RequestParam(required = false) Long driverId,
            @RequestParam String trackingNumber) {
        return ResponseEntity.ok(shipmentService.createShipment(customerId, driverId, trackingNumber));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(
            @PathVariable Long id, 
            @RequestBody ShipmentStatusUpdateRequest status) {
        return ResponseEntity.ok(shipmentService.updateShipmentStatus(id, status.getStatus(), status.getDeliveryInstructions()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }

    static class ShipmentStatusUpdateRequest {
        private ShipmentStatus status;
        private String deliveryInstructions;

        public ShipmentStatus getStatus() {
            return status;
        }

        public void setStatus(ShipmentStatus status) {
            this.status = status;
        }

        public String getDeliveryInstructions() {
            return deliveryInstructions;
        }

        public void setDeliveryInstructions(String deliveryInstructions) {
            this.deliveryInstructions = deliveryInstructions;
        }
    }
}
