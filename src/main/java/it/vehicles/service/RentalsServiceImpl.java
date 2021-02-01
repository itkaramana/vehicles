package it.vehicles.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import it.vehicles.entity.Rental;
import it.vehicles.input.RentalsInput;
import it.vehicles.repository.RentalsRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RentalsServiceImpl implements RentalsService {

	@Autowired
	private RentalsRepository repository;

	private Integer offset;

	@Retryable(value = SQLException.class, maxAttemptsExpression = "${retry.max.attempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
	@Override
	public List<Rental> initListRentals(RentalsInput input) {
		List<Rental> rentals = new ArrayList<Rental>();
		if (input.getOffset() == null) {
			offset = 0;
		} else {
			offset = input.getOffset();
		}

		if (input.getIds() != null) {
			if (Strings.isNotBlank(input.getSort())) {
				rentals = repository.findByIdIn(input.getIds(), Sort.by(Sort.Direction.ASC, input.getSort()));
			} else {
				rentals = repository.findByIdIn(input.getIds(), null);
			}
		}

		if (input.getPriceMin() != null && input.getPriceMax() == null) {
			if (Strings.isNotBlank(input.getSort()) && input.getLimit() != null) {

				rentals = repository.findByPriceGreaterAndPaginated(input.getPriceMin(), input.getLimit(), offset,
						PageRequest.of(0, input.getLimit(), Sort.Direction.ASC, input.getSort()));

			} else if (Strings.isNotBlank(input.getSort())) {
				rentals = repository.findByPricePerDayGreaterThanEqual(input.getPriceMin(),
						Sort.by(Sort.Direction.ASC, input.getSort()));
			} else if (input.getLimit() != null) {
				rentals = repository.findByPriceGreaterAndPaginated(input.getPriceMin(), input.getLimit(), offset,
						null);
			} else {
				rentals = repository.findByPricePerDayGreaterThanEqual(input.getPriceMin(), null);
			}
		} else if (input.getPriceMin() == null && input.getPriceMax() != null) {
			if (Strings.isNotBlank(input.getSort()) && input.getLimit() != null) {

				rentals = repository.findByPriceGreaterAndPaginated(input.getPriceMax(), input.getLimit(), offset,
						PageRequest.of(0, input.getLimit(), Sort.Direction.DESC, input.getSort()));

			} else if (Strings.isNotBlank(input.getSort())) {
				rentals = repository.findByPricePerDayGreaterThanEqual(input.getPriceMax(),
						Sort.by(Sort.Direction.ASC, input.getSort()));
			} else if (input.getLimit() != null) {
				rentals = repository.findByPriceGreaterAndPaginated(input.getPriceMax(), input.getLimit(), offset,
						null);
			} else {
				rentals = repository.findByPricePerDayGreaterThanEqual(input.getPriceMax(), null);
			}
		} else if (input.getPriceMin() != null && input.getPriceMax() != null) {
			log.info("query by priceMin and priceMax");
			// if (Strings.isNotBlank(input.getSort())) {
			// List<Rental> rentalsByPrice =
			// repository.findByPricePerDayBetween(input.getPriceMin(),
			// input.getPriceMax(), Sort.by(Sort.Direction.ASC, input.getSort().trim()));
			//
			// rentals = rentalsByPrice;
			// } else
			if (input.getLimit() != null) {
				if (Strings.isBlank(input.getSort())) {
					rentals = repository.findBetweenPricePaginated(input.getPriceMin(), input.getPriceMax(),
							input.getLimit(), offset, null);
				} else {
					rentals = repository.findBetweenPricePaginated(input.getPriceMin(), input.getPriceMax(),
							input.getLimit(), offset,
							PageRequest.of(0, input.getLimit(), Sort.Direction.ASC, input.getSort()));
				}
			} else {
				List<Rental> rentalsByPrice = repository.findByPricePerDayBetween(input.getPriceMin(),
						input.getPriceMax(), null);
				rentals = rentalsByPrice;
			}

			log.info("Count of returned entities: " + rentals.size());
		}

		if (input.getNear() != null) {
			if (input.getLimit() != null) {
				if (input.getOffset() == null) {
					input.setOffset(0);
				}
				rentals = repository.findByLtaAndLngPaginated(input.getNear()[0], input.getNear()[1], input.getLimit(),
						offset, PageRequest.of(0, input.getLimit(), Sort.by(Sort.Direction.ASC, input.getSort())));
			} else if (Strings.isNotBlank(input.getSort())) {

				rentals = repository.findByLtaAndLng(input.getNear()[0], input.getNear()[1],
						PageRequest.of(0, 1000, Sort.Direction.ASC, input.getSort()));
			} else {
				rentals = repository.findByLtaAndLng(input.getNear()[0], input.getNear()[1], null);
			}

			if (Strings.isNotBlank(input.getSort())) {

			}
		}

		// if (Strings.isNotBlank(input.getSort())) {
		// if (input.getLimit() != null) {
		// if (input.getOffset() == null) {
		// input.setOffset(0);
		// }
		// rentals = repository.findByLimitAndOffset(input.getLimit(), offset,
		// PageRequest.of(0, input.getLimit(), Sort.Direction.ASC, input.getSort()));
		// } else {
		// rentals = repository.findAll(Sort.by(Sort.Direction.ASC,
		// input.getSort().trim()));
		// }
		// }

		// if (input.getLimit() != null && Strings.isBlank(input.getSort())) {
		// if (input.getOffset() == null) {
		// input.setOffset(0);
		// }
		// rentals = repository.findByLimitAndOffset(input.getLimit(), offset);
		// }
		return rentals;

	}

}
