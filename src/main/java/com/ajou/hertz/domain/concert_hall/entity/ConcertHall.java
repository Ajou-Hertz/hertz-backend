package com.ajou.hertz.domain.concert_hall.entity;

import com.ajou.hertz.common.entity.FullAddress;
import com.ajou.hertz.common.entity.TimeTrackedBaseEntity;
import com.ajou.hertz.domain.concert_hall.constant.ConcertHallProgressStatus;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ConcertHall extends TimeTrackedBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "concert_hall_id", nullable = false)
	private Long id;

	@JoinColumn(name = "seller_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User seller;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ConcertHallProgressStatus progressStatus;

	@Column(nullable = false)
	@Embedded
	private FullAddress fullAddress;

	@Column(nullable = false)
	private Boolean hasSoundEquipment;

	@Column(nullable = false)
	private Boolean hasStaff;

	@Column(nullable = false)
	private Boolean hasAdditionalSpace;

	@Column(nullable = false)
	private Integer pricePerDay;

	@Column(nullable = false)
	private Integer pricePerHour;

	@Column(columnDefinition = "smallint", nullable = false)
	private Integer capacity;

	@Column(nullable = false)
	private String size;

	@Column(nullable = false)
	private Boolean hasParkingLot;

	@Column(length = 1000, nullable = false)
	private String description;

	@Embedded
	private ConcertHallImages images = new ConcertHallImages();

	@Embedded
	private ConcertHallHashtags hashtags = new ConcertHallHashtags();

	private ConcertHall(
		Long id,
		User seller,
		String title,
		ConcertHallProgressStatus progressStatus,
		FullAddress fullAddress,
		Boolean hasSoundEquipment,
		Boolean hasStaff,
		Boolean hasAdditionalSpace,
		Integer pricePerDay,
		Integer pricePerHour,
		Integer capacity,
		String size,
		Boolean hasParkingLot,
		String description
	) {
		this.id = id;
		this.seller = seller;
		this.title = title;
		this.progressStatus = progressStatus;
		this.fullAddress = fullAddress;
		this.hasSoundEquipment = hasSoundEquipment;
		this.hasStaff = hasStaff;
		this.hasAdditionalSpace = hasAdditionalSpace;
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.capacity = capacity;
		this.size = size;
		this.hasParkingLot = hasParkingLot;
		this.description = description;
	}
}
