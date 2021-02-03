package it.vehicles.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.vehicles.entity.Rental;

public interface RentalsApi {

	@RequestMapping(value = "/rvs/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Rental> retrieveRental(@PathVariable(value = "id", required = false) String id);

	@RequestMapping(value = "/rvs", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Rental>> listRentals(@RequestParam(value = "ids", required = false) List<Long> ids,
			@RequestParam(value = "price[min]", required = false) Long priceMin,
			@RequestParam(value = "price[max]", required = false) Long priceMax,
			@RequestParam(value = "near", required = false) Double[] near,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "page[offset]", required = false) Integer offset,
			@RequestParam(value = "page[limit]", required = false) Integer limit);
}
