package it.vehicles.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.vehicles.entity.Rental;

@Repository
public interface RentalsRepository extends JpaRepository<Rental, Long> {

	public List<Rental> findByIdIn(List<Long> ids, Sort sort);

	public List<Rental> findByPricePerDayBetween(Long priceMin, Long priceMax, Sort sort);

	@Query(value = "SELECT * FROM rentals WHERE price_per_day BETWEEN ?1 AND ?2 LIMIT ?3 OFFSET ?4", nativeQuery = true)
	public List<Rental> findBetweenPricePaginated(Long priceMin, Long priceMax, Integer limit, Integer offset,
			Pageable pageable);

	@Query(value = "SELECT * FROM rentals ORDER BY id LIMIT ?1 OFFSET ?2", nativeQuery = true)
	public List<Rental> findByLimitAndOffset(Integer limit, Integer offset);

	@Query(value = "SELECT * FROM rentals ORDER BY ?1 ASC LIMIT ?2 OFFSET ?3", nativeQuery = true)
	public List<Rental> findByLimitAndOffsetSorted(String sort, Integer limit, Integer offset);

	@Query(value = "SELECT * FROM rentals AS r WHERE earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4", nativeQuery = true)
	public List<Rental> findByLtaAndLng(Double lta, Double lng, Pageable pageable);

	@Query(value = "SELECT * FROM rentals AS r WHERE earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4 LIMIT ?3 OFFSET ?4", nativeQuery = true)
	public List<Rental> findByLtaAndLngPaginated(Double lta, Double lng, Integer limit, Integer offset,
			Pageable pageable);

	// @Query(value = "SELECT * FROM rentals AS r WHERE
	// earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4
	// ORDER BY ?3", nativeQuery = true)
	// public List<Rental> findByLtaAndLng(Double lta, Double lng, String sort);
}
