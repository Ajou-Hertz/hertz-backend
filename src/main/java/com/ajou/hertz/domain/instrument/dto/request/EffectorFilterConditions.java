package com.ajou.hertz.domain.instrument.dto.request;

import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class EffectorFilterConditions extends InstrumentFilterConditions {

	private EffectorType type;
	private EffectorFeature feature;

	private EffectorFilterConditions(
		InstrumentProgressStatus progress,
		String sido,
		String sgg,
		EffectorType type,
		EffectorFeature feature
	) {
		super(progress, sido, sgg);
		this.type = type;
		this.feature = feature;
	}
}
