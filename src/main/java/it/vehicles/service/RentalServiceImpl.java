package it.vehicles.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sentry.spring.EnableSentry;
import it.vehicles.entity.Rental;
import it.vehicles.exceptions.NotFoundException;
import it.vehicles.repository.RentalsRepository;

@Service
@EnableSentry
public class RentalServiceImpl implements RentalService {

	@Autowired
	private RentalsRepository repository;

	@Override
	public Rental initRetriveRental(String id) {

		Long idToDb = Long.parseLong(id);

		Optional<Rental> vehicle = repository.findById(idToDb);

		if (!vehicle.isPresent()) {
			throw new NotFoundException("mememe", 603, "sdsadsa");
		}

		return vehicle.get();
	}

}
