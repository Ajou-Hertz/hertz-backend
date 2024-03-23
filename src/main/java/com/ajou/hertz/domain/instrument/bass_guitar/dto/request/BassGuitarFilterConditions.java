package com.ajou.hertz.domain.instrument.bass_guitar.dto.request;

import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class BassGuitarFilterConditions extends InstrumentFilterConditions {

	private BassGuitarBrand brand;
	private BassGuitarPickUp pickUp;
	private BassGuitarPreAmplifier preAmplifier;
	private GuitarColor color;

	private BassGuitarFilterConditions(
		InstrumentProgressStatus progress,
		String sido,
		String sgg,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) {
		super(progress, sido, sgg);
		this.brand = brand;
		this.pickUp = pickUp;
		this.preAmplifier = preAmplifier;
		this.color = color;
	}
}
