package it.vehicles.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vehicles.entity.Rental;
import it.vehicles.exceptions.NotFoundException;
import it.vehicles.repository.RentalsRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RentalServiceImpl implements RentalService {

	@Autowired
	private RentalsRepository repository;

	@Override
	public Rental initRetriveRental(String id) {

		log.info("Entering retrieveVehicle by ID operation");
		Long idToDb = Long.parseLong(id);

		Optional<Rental> vehicle = repository.findById(idToDb);

		if (!vehicle.isPresent()) {
			throw new NotFoundException("mememe", 603, "sdsadsa");
		}

		return vehicle.get();
	}

}
