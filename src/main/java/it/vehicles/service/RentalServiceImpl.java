package it.vehicles.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vehicles.entity.Rental;
import it.vehicles.exceptions.NotFoundException;
import it.vehicles.repository.RentalsRepository;
import it.vehicles.util.Constant;

@Service
public class RentalServiceImpl implements RentalService {

	@Autowired
	private RentalsRepository repository;

	@Override
	public Rental initRetriveRental(String id) {

		Long idToDb = Long.parseLong(id);

		Optional<Rental> vehicle = repository.findById(idToDb);

		if (!vehicle.isPresent()) {
			throw new NotFoundException("Vehichle with id: " + id + " not found.", Constant.ERROR_404_NOTFOUND);
		}

		return vehicle.get();
	}

}
