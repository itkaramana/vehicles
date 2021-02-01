package it.vehicles.input;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class RentalsInput {

	List<Long> ids;
	Integer limit;
	Integer offset;
	Long priceMin;
	Long priceMax;
	Double[] near;
	String sort;

	@Builder(toBuilder = true)
	public RentalsInput(List<Long> ids, Integer limit, Integer offset, Long priceMin, Long priceMax, Double[] near,
			String sort) {
		this.ids = ids;
		this.limit = limit;
		this.offset = offset;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.near = near;
		this.sort = sort;
	}
}
