package com.ajou.hertz.domain.instrument.dto.request;

import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class AcousticAndClassicGuitarFilterConditions extends InstrumentFilterConditions {

	private AcousticAndClassicGuitarBrand brand;
	private AcousticAndClassicGuitarModel model;
	private AcousticAndClassicGuitarWood wood;
	private AcousticAndClassicGuitarPickUp pickUp;

	private AcousticAndClassicGuitarFilterConditions(
		InstrumentProgressStatus progress,
		String sido,
		String sgg,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) {
		super(progress, sido, sgg);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}
}
