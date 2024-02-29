package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("ELECTRIC_GUITAR")
@Entity
public class ElectricGuitar extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ElectricGuitarBrand brand;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ElectricGuitarModel model;

	@Column(nullable = false)
	private Short productionYear;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GuitarColor color;

	private ElectricGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.color = color;
	}

	public static ElectricGuitar create(
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) {
		return new ElectricGuitar(
			null, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, brand, model, productionYear, color
		);
	}
}
