package com.ajou.hertz.domain.instrument.audio_equipment.dto.request;

import com.ajou.hertz.domain.instrument.audio_equipment.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class AudioEquipmentFilterConditions extends InstrumentFilterConditions {

	private AudioEquipmentType type;

	private AudioEquipmentFilterConditions(
		InstrumentProgressStatus progress,
		String sido,
		String sgg,
		AudioEquipmentType type
	) {
		super(progress, sido, sgg);
		this.type = type;
	}
}
