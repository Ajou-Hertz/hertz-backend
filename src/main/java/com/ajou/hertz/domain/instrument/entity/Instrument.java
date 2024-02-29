package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.common.entity.TimeTrackedBaseEntity;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorColumn(name = "category")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Instrument extends TimeTrackedBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "instrument_id", nullable = false)
	private Long id;

	@JoinColumn(name = "seller_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User seller;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private InstrumentProgressStatus progressStatus;

	@Embedded
	private Address tradeAddress;

	@Column(nullable = false)
	private Short qualityStatus;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Boolean hasAnomaly;

	@Column(length = 1000, nullable = false)
	private String description;

	@Embedded
	private InstrumentImages images = new InstrumentImages();

	@Embedded
	private InstrumentHashtags hashtags = new InstrumentHashtags();

	protected Instrument(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description
	) {
		this.id = id;
		this.seller = seller;
		this.title = title;
		this.progressStatus = progressStatus;
		this.tradeAddress = tradeAddress;
		this.qualityStatus = qualityStatus;
		this.price = price;
		this.hasAnomaly = hasAnomaly;
		this.description = description;
	}
}
