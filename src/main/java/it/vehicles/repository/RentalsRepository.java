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

	@Query(value = "Select * FROM rentals WHERE price_per_day > ?1", nativeQuery = true)
	public List<Rental> findByPriceBigger(Long pricePerDay, Pageable pageable);

	@Query(value = "Select * FROM rentals WHERE price_per_day < ?1", nativeQuery = true)
	public List<Rental> findByPriceLesser(Long pricePerDay, Pageable pageable);

	@Query(value = "SELECT * FROM rentals WHERE price_per_day BETWEEN ?1 AND ?2", nativeQuery = true)
	public List<Rental> findByPriceBetween(Long priceMin, Long priceMax, Pageable pageable);

	@Query(value = "SELECT * FROM rentals AS r WHERE earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4", nativeQuery = true)
	public List<Rental> findByLtaAndLng(Double lta, Double lng, Pageable pageable);

	@Query(value = "SELECT * FROM rentals AS r WHERE earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4 AND price_per_day > ?3", nativeQuery = true)
	public List<Rental> findByNearAndPriceBigger(Double lta, Double lng, Long pricePerDay, Pageable pageable);

	@Query(value = "SELECT * FROM rentals AS r WHERE earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4 AND price_per_day < ?3", nativeQuery = true)
	public List<Rental> findByNearAndPriceLesser(Double lta, Double lng, Long pricePerDay, Pageable pageable);

	@Query(value = "SELECT * FROM rentals AS r WHERE earth_distance(ll_to_earth(r.lat, r.lng), ll_to_earth(?1,?2)) < 160934.4 AND price_per_day BETWEEN ?3 AND ?4", nativeQuery = true)
	public List<Rental> findByNearAndPriceBetween(Double lta, Double lng, Long priceMin, Long priceMax,
			Pageable pageable);

}
