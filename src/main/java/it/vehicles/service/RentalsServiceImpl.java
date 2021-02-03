package it.vehicles.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import it.vehicles.entity.Rental;
import it.vehicles.exceptions.BadRequestException;
import it.vehicles.input.RentalsInput;
import it.vehicles.repository.RentalsRepository;
import it.vehicles.util.Constant;
import it.vehicles.util.OffsetPageable;

@Service
public class RentalsServiceImpl implements RentalsService {

	@Autowired
	private RentalsRepository repository;

	private Integer offset;

	private int defaultLimit;

	@Retryable(value = SQLException.class, maxAttemptsExpression = "${retry.max.attempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
	@Override
	public List<Rental> initListRentals(RentalsInput input) {

		// if none of the query parameters is not passed returning all of the records
		if (input.getIds() == null && input.getLimit() == null && input.getNear() == null && input.getOffset() == null
				&& input.getPriceMax() == null && input.getPriceMin() == null && input.getSort() == null) {
			return repository.findAll();
		}

		if (input.getNear() != null && input.getNear().length != 2) {
			throw new BadRequestException("near should be with 2 parameters separated by comma",
					Constant.ERROR_400_BADREQUEST, Constant.BAD_REQUEST_MESSAGE);
		}

		OffsetPageable pageable = null;
		// cashing the count of records
		this.defaultLimit = (int) repository.count();

		List<Rental> rentals = new ArrayList<Rental>();

		// to secure the offset if limit is passed
		if (input.getOffset() == null) {
			offset = 0;
		} else {
			offset = input.getOffset();
		}

		// if the limit is passed, there is only logic to be > 0
		if (input.getLimit() != null && input.getLimit() < 1) {
			throw new BadRequestException("limit should be bigger than 0", Constant.ERROR_400_BADREQUEST,
					Constant.BAD_REQUEST_MESSAGE);
		}

		// if the ids parameter is passed, only sorting should be enabled, ignoring the
		// other parameters, because there is no logic to filter when we search by index
		// in this case the user knows what to expect
		if (input.getIds() != null) {
			if (Strings.isNotBlank(input.getSort())) {
				return rentals = repository.findByIdIn(input.getIds(), Sort.by(Sort.Direction.ASC, input.getSort()));
			} else {
				return rentals = repository.findByIdIn(input.getIds(), null);
			}
		}

		// declaring pagination and sorting by the input or set to default
		if (Strings.isNotBlank(input.getSort()) && input.getLimit() != null) {
			pageable = new OffsetPageable(input.getLimit(), offset, input.getSort());
		} else if (Strings.isNotBlank(input.getSort())) {
			// default limit = count of the records
			pageable = new OffsetPageable(defaultLimit, offset, input.getSort());
		} else if (input.getLimit() != null) {
			// default sorting by id
			pageable = new OffsetPageable(input.getLimit(), offset, "id");
		} else {
			// default values for sorting and pagination
			pageable = new OffsetPageable(defaultLimit, offset, "id");
		}

		// filter by price, with pagination and sort if they are passed
		if (input.getPriceMin() != null && input.getPriceMax() == null) {
			rentals = repository.findByPriceBigger(input.getPriceMin(), pageable);
		} else if (input.getPriceMin() == null && input.getPriceMax() != null) {
			rentals = repository.findByPriceLesser(input.getPriceMax(), pageable);
		} else if (input.getPriceMin() != null && input.getPriceMax() != null) {
			rentals = repository.findByPriceBetween(input.getPriceMin(), input.getPriceMax(), pageable);
		}

		// near with addition filter by price, possible sort and pagination if they are
		// passed
		if (input.getNear() != null) {
			if (input.getPriceMax() != null && input.getPriceMin() != null) {
				rentals = repository.findByNearAndPriceBetween(input.getNear()[0], input.getNear()[1],
						input.getPriceMin(), input.getPriceMax(), pageable);
			} else if (input.getPriceMin() != null) {
				rentals = repository.findByNearAndPriceBigger(input.getNear()[0], input.getNear()[1],
						input.getPriceMin(), pageable);
			} else if (input.getPriceMax() != null) {
				rentals = repository.findByNearAndPriceLesser(input.getNear()[0], input.getNear()[1],
						input.getPriceMax(), pageable);
			} else {
				rentals = repository.findByLtaAndLng(input.getNear()[0], input.getNear()[1], pageable);
			}

		}

		// it would catch if sort is passed
		if (input.getLimit() != null && input.getNear() == null && input.getPriceMax() == null
				&& input.getPriceMin() == null && input.getOffset() == null) {
			Page<Rental> page = repository.findAll(pageable);
			rentals = page.getContent();
		}

		// only sort is passed, other cases are covered in the previues conditions
		if (Strings.isNotBlank(input.getSort()) && input.getPriceMax() == null && input.getPriceMin() == null
				&& input.getLimit() == null && input.getOffset() == null && input.getNear() == null) {
			rentals = repository.findAll(Sort.by(Sort.Direction.ASC, input.getSort()));
		}

		return rentals;

	}

}
