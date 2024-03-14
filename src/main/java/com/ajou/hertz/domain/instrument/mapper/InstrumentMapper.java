package com.ajou.hertz.domain.instrument.mapper;

import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.response.AcousticAndClassicGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.AmplifierResponse;
import com.ajou.hertz.domain.instrument.dto.response.AudioEquipmentResponse;
import com.ajou.hertz.domain.instrument.dto.response.BassGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.EffectorResponse;
import com.ajou.hertz.domain.instrument.dto.response.ElectricGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentResponse;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstrumentMapper {

	public static InstrumentDto toDto(Instrument instrument) {
		if (instrument instanceof ElectricGuitar) {
			return ElectricGuitarDto.from((ElectricGuitar)instrument);
		} else if (instrument instanceof BassGuitar) {
			return BassGuitarDto.from((BassGuitar)instrument);
		} else if (instrument instanceof AcousticAndClassicGuitar) {
			return AcousticAndClassicGuitarDto.from((AcousticAndClassicGuitar)instrument);
		} else if (instrument instanceof Effector) {
			return EffectorDto.from((Effector)instrument);
		} else if (instrument instanceof Amplifier) {
			return AmplifierDto.from((Amplifier)instrument);
		} else {
			return AudioEquipmentDto.from((AudioEquipment)instrument);
		}
	}

	public static InstrumentResponse toResponse(InstrumentDto instrumentDto) {
		if (instrumentDto instanceof ElectricGuitarDto) {
			return ElectricGuitarResponse.from((ElectricGuitarDto)instrumentDto);
		} else if (instrumentDto instanceof BassGuitarDto) {
			return BassGuitarResponse.from((BassGuitarDto)instrumentDto);
		} else if (instrumentDto instanceof AcousticAndClassicGuitarDto) {
			return AcousticAndClassicGuitarResponse.from((AcousticAndClassicGuitarDto)instrumentDto);
		} else if (instrumentDto instanceof EffectorDto) {
			return EffectorResponse.from((EffectorDto)instrumentDto);
		} else if (instrumentDto instanceof AmplifierDto) {
			return AmplifierResponse.from((AmplifierDto)instrumentDto);
		} else {
			return AudioEquipmentResponse.from((AudioEquipmentDto)instrumentDto);
		}
	}
}
