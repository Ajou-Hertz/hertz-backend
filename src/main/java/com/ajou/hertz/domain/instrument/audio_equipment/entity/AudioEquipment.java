package com.ajou.hertz.domain.instrument.audio_equipment.entity;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.audio_equipment.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Instrument;
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
@DiscriminatorValue("AUDIO_EQUIPMENT")
@Entity
public class AudioEquipment extends Instrument {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AudioEquipmentType type;

	private AudioEquipment(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AudioEquipmentType type
	) {
		super(id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description);
		this.type = type;
	}

	public static AudioEquipment create(
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AudioEquipmentType type
	) {
		return new AudioEquipment(
			null, seller, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, type
		);
	}
}
