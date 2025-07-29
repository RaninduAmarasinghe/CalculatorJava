package com.busbuddy.busbuddy.service;

import com.busbuddy.busbuddy.model.Bus;
import com.busbuddy.busbuddy.model.Location;
import com.busbuddy.busbuddy.repository.BusRepo;
import com.busbuddy.busbuddy.Util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusService {

    @Autowired
    private BusRepo busRepo;
    @Autowired
    private CustomIdGenerator customIdGenerator;


    // Create a new bus and assign it to a company
    public Bus createBus(Bus bus, String companyId) {
        String busId = customIdGenerator.generateUniqueId("B", "bus");
        bus.setBusId(busId);
        bus.setCompanyId(companyId);
        return busRepo.save(bus);
    }
    // Update existing bus by ID
    public boolean updateBus(String busId, Bus updatedBus) {
        Optional<Bus> busOpt = busRepo.findById(busId);
        if (busOpt.isPresent()) {
            Bus existing = busOpt.get();
            existing.setBusNumber(updatedBus.getBusNumber());
            existing.setRoutes(updatedBus.getRoutes());
            busRepo.save(existing);
            return true;
        }
        return false;
    }

    //  Delete bus by ID
    public boolean deleteBus(String busId) {
        if (busRepo.existsById(busId)) {
            busRepo.deleteById(busId);
            return true;
        }
        return false;
    }

    // Retrieve all buses belonging to a company
    public List<Bus> getBusesByCompany(String companyId) {
        return busRepo.findByCompanyId(companyId);
    }

    // Retrieve all buses assigned to a specific driver
    public List<Bus> getBusesByDriver(String driverId) {
        return busRepo.findByDriverId(driverId);
    }

    // Retrieve a single bus by its ID
    public Bus getBusById(String busId) {
        Optional<Bus> bus = busRepo.findById(busId);
        return bus.orElse(null);
    }

    // Update the status of a bus (e.g., "Running", "Stopped")
    public boolean updateBusStatus(String busId, String status) {
        Optional<Bus> busOpt = busRepo.findById(busId);
        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();
            bus.setStatus(status); // Ensure 'status' field exists in the Bus model
            busRepo.save(bus);
            return true;
        }
        return false;
    }

    // Retrieve all active buses (status = "Running" and has valid route info)
    public List<Bus> getActiveBuses() {
        List<Bus> runningBuses = busRepo.findByStatus("Running");
        return runningBuses.stream()
                .filter(bus -> bus.getRoutes() != null && !bus.getRoutes().isEmpty())
                .collect(Collectors.toList());
    }

    // Update the location of a bus (latitude & longitude)
    public boolean updateBusLocation(String busId, Location location) {
        Optional<Bus> busOpt = busRepo.findById(busId);
        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();
            bus.setLocation(location); // Set the updated location
            busRepo.save(bus);  // Save the updated bus document in the database
            System.out.println("Updated bus location: " + bus.getLocation());
            return true;
        }
        return false;
    }

    public List<Bus> getAllBuses() {
        return busRepo.findAll();
    }

}