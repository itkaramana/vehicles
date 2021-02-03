package it.vehicles.service;

import java.util.List;

import it.vehicles.entity.Rental;
import it.vehicles.input.RentalsInput;

public interface RentalsService {

	List<Rental> initListRentals(RentalsInput input);
}
