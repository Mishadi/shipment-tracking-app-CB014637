package mishadi.trackingsystem.repositories;

import mishadi.trackingsystem.entities.Shipment;
import mishadi.trackingsystem.entities.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByStatus(ShipmentStatus status);

    long countByStatus(ShipmentStatus status);

    @Query("SELECT COUNT(DISTINCT s.driver) FROM Shipment s WHERE s.driver IS NOT NULL")
    long countDistinctDrivers();

    @Query("SELECT COUNT(DISTINCT s.customer) FROM Shipment s")
    long countDistinctCustomers();

    @Query("SELECT NEW map(d.name as driverName, " +
           "COUNT(s) as totalShipments, " +
           "SUM(CASE WHEN s.status = 'DELIVERED' THEN 1 ELSE 0 END) as deliveredShipments) " +
           "FROM Shipment s JOIN s.driver d GROUP BY d.id")
    List<Map<String, Object>> getDriverDeliveryStats();

    @Query("SELECT NEW map(c.name as customerName, COUNT(s) as shipmentCount) " +
           "FROM Shipment s JOIN s.customer c GROUP BY c.id ORDER BY COUNT(s) DESC")
    List<Map<String, Object>> getTopCustomersByShipmentCount();

    @Query("SELECT AVG(TIMESTAMPDIFF(HOUR, s.createdAt, s.updatedAt)) " +
           "FROM Shipment s WHERE s.status = 'DELIVERED'")
    Double getAverageDeliveryTime();
}
