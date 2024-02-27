package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassGuitarWood;
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
public class AcousticAndClassGuitar extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassGuitarBrand brand;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassGuitarModel model;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassGuitarWood wood;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AcousticAndClassGuitarPickUp pickUp;

	private AcousticAndClassGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AcousticAndClassGuitarBrand brand,
		AcousticAndClassGuitarModel model,
		AcousticAndClassGuitarWood wood,
		AcousticAndClassGuitarPickUp pickUp
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}

	public static AcousticAndClassGuitar create(
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AcousticAndClassGuitarBrand brand,
		AcousticAndClassGuitarModel model,
		AcousticAndClassGuitarWood wood,
		AcousticAndClassGuitarPickUp pickUp
	) {
		return new AcousticAndClassGuitar(
			null, seller, title, progressStatus, tradeAddress, qualityStatus,
			price, hasAnomaly, description, brand, model, wood, pickUp
		);
	}
}
