package com.ajou.hertz.domain.instrument.dto.request;

import com.ajou.hertz.domain.instrument.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class AmplifierFilterConditions extends InstrumentFilterConditions {

	private AmplifierType type;
	private AmplifierBrand brand;
	private AmplifierUsage usage;

	private AmplifierFilterConditions(
		InstrumentProgressStatus progress,
		String sido,
		String sgg,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) {
		super(progress, sido, sgg);
		this.type = type;
		this.brand = brand;
		this.usage = usage;
	}
}
