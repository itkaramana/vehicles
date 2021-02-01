package it.vehicles.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.vehicles.entity.Rental;
import it.vehicles.input.RentalsInput;
import it.vehicles.service.RentalService;
import it.vehicles.service.RentalsService;

@Controller
public class RentalsApiController implements RentalsApi {

	private RentalService retrieveVehicleService;

	private RentalsService listVehiclesService;

	public RentalsApiController(RentalService retrieveVehicleService, RentalsService listVehiclesService) {
		this.retrieveVehicleService = retrieveVehicleService;
		this.listVehiclesService = listVehiclesService;

	}

	@RequestMapping(value = "/rvs/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	@Override
	public ResponseEntity<Rental> retrieveRental(@PathVariable(value = "id", required = true) String id) {
		return new ResponseEntity<>(retrieveVehicleService.initRetriveRental(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/rvs", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	@Override
	public ResponseEntity<List<Rental>> listRentals(@RequestParam(value = "ids", required = false) List<Long> ids,
			@RequestParam(value = "price[min]", required = false) Long priceMin,
			@RequestParam(value = "price[max]", required = false) Long priceMax,
			@RequestParam(value = "near", required = false) Double[] near,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "page[offset]", required = false) Integer offset,
			@RequestParam(value = "page[limit]", required = false) Integer limit) {
		RentalsInput input = RentalsInput.builder().ids(ids).priceMin(priceMin).priceMax(priceMax).near(near).sort(sort).offset(offset).limit(limit).build();
		return new ResponseEntity<>(listVehiclesService.initListRentals(input), HttpStatus.OK);
	}

}
