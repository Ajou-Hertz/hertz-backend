package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
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
@DiscriminatorValue("ACOUSTIC_AND_CLASS_GUITAR")
@Entity
public class AcousticAndClassicGuitar extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassicGuitarBrand brand;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassicGuitarModel model;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassicGuitarWood wood;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassicGuitarPickUp pickUp;

	private AcousticAndClassicGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}

	public static AcousticAndClassicGuitar create(
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) {
		return new AcousticAndClassicGuitar(
			null, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, brand, model, wood, pickUp
		);
	}
}
