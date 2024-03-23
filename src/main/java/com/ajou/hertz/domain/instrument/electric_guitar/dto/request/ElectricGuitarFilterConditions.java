package com.ajou.hertz.domain.instrument.electric_guitar.dto.request;

import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class ElectricGuitarFilterConditions extends InstrumentFilterConditions {

	private ElectricGuitarBrand brand;
	private ElectricGuitarModel model;
	private GuitarColor color;

	private ElectricGuitarFilterConditions(
		InstrumentProgressStatus progress,
		String sido,
		String sgg,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		GuitarColor color
	) {
		super(progress, sido, sgg);
		this.brand = brand;
		this.model = model;
		this.color = color;
	}
}
