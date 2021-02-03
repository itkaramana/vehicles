package it.vehicles.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "rentals")
@Data
public class Rental {

	@Id
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "description")
	private String description;

	@Column(name = "sleeps")
	private int sleeps;

	@Column(name = "price_per_day")
	private long pricePerDay;

	@Column(name = "home_city")
	private String homeCity;

	@Column(name = "home_state")
	private String homeState;

	@Column(name = "home_zip")
	private String homeZip;

	@Column(name = "home_country")
	private String homeCountry;

	@Column(name = "vehicle_make")
	private String vehicleMake;

	@Column(name = "vehicle_model")
	private String vehicleModel;

	@Column(name = "vehicle_year")
	private int vehicleYear;

	@Column(name = "vehicle_length")
	private BigDecimal vehicleLength;

	@Column(name = "created")
	private Timestamp created;

	@Column(name = "updated")
	private Timestamp updated;

	@Column(name = "lat")
	private BigDecimal lat;

	@Column(name = "lng")
	private BigDecimal lng;

	@Column(name = "primary_image_url")
	private String primaryImageUrl;

	@Column(name = "owner_name")
	private String ownerName;

	@Column(name = "owner_avatar_url")
	private String ownerAvatarUrl;

}
