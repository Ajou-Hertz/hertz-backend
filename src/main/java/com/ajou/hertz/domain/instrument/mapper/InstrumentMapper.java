package com.ajou.hertz.domain.instrument.mapper;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.response.AcousticAndClassicGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.AmplifierResponse;
import com.ajou.hertz.domain.instrument.dto.response.AudioEquipmentResponse;
import com.ajou.hertz.domain.instrument.dto.response.BassGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.EffectorResponse;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.response.ElectricGuitarResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentImageResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentResponse;
import com.ajou.hertz.domain.instrument.dto.response.InstrumentSummaryResponse;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstrumentMapper {

	public static InstrumentDto toDto(Instrument instrument) {
		if (instrument instanceof ElectricGuitar) {
			return new ElectricGuitarDto((ElectricGuitar)instrument);
		} else if (instrument instanceof BassGuitar) {
			return new BassGuitarDto((BassGuitar)instrument);
		} else if (instrument instanceof AcousticAndClassicGuitar) {
			return new AcousticAndClassicGuitarDto((AcousticAndClassicGuitar)instrument);
		} else if (instrument instanceof Effector) {
			return new EffectorDto((Effector)instrument);
		} else if (instrument instanceof Amplifier) {
			return new AmplifierDto((Amplifier)instrument);
		} else {
			return new AudioEquipmentDto((AudioEquipment)instrument);
		}
	}

	public static ElectricGuitarDto toElectricGuitarDto(ElectricGuitar electricGuitar) {
		return new ElectricGuitarDto(electricGuitar);
	}

	public static BassGuitarDto toBassGuitarDto(BassGuitar bassGuitar) {
		return new BassGuitarDto(bassGuitar);
	}

	public static AcousticAndClassicGuitarDto toAcousticAndClassicGuitarDto(
		AcousticAndClassicGuitar acousticAndClassicGuitar
	) {
		return new AcousticAndClassicGuitarDto(acousticAndClassicGuitar);
	}

	public static EffectorDto toEffectorDto(Effector effector) {
		return new EffectorDto(effector);
	}

	public static AmplifierDto toAmplifierDto(Amplifier amplifier) {
		return new AmplifierDto(amplifier);
	}

	public static AudioEquipmentDto toAmplifierDto(AudioEquipment audioEquipment) {
		return new AudioEquipmentDto(audioEquipment);
	}

	public static InstrumentResponse toResponse(InstrumentDto instrumentDto) {
		if (instrumentDto instanceof ElectricGuitarDto) {
			return new ElectricGuitarResponse((ElectricGuitarDto)instrumentDto);
		} else if (instrumentDto instanceof BassGuitarDto) {
			return new BassGuitarResponse((BassGuitarDto)instrumentDto);
		} else if (instrumentDto instanceof AcousticAndClassicGuitarDto) {
			return new AcousticAndClassicGuitarResponse((AcousticAndClassicGuitarDto)instrumentDto);
		} else if (instrumentDto instanceof EffectorDto) {
			return new EffectorResponse((EffectorDto)instrumentDto);
		} else if (instrumentDto instanceof AmplifierDto) {
			return new AmplifierResponse((AmplifierDto)instrumentDto);
		} else {
			return new AudioEquipmentResponse((AudioEquipmentDto)instrumentDto);
		}
	}

	public static InstrumentSummaryResponse toInstrumentSummaryResponse(InstrumentDto instrumentDto) {
		return new InstrumentSummaryResponse(
			instrumentDto.getId(),
			instrumentDto.getCategory(),
			instrumentDto.getTitle(),
			instrumentDto.getProgressStatus(),
			AddressResponse.from(instrumentDto.getTradeAddress()),
			instrumentDto.getPrice(),
			InstrumentImageResponse.from(
				instrumentDto.getImages().isEmpty() ? null : instrumentDto.getImages().get(0)
			)
		);
	}

	public static ElectricGuitarResponse toElectricGuitarResponse(ElectricGuitarDto electricGuitarDto) {
		return new ElectricGuitarResponse(electricGuitarDto);
	}

	public static BassGuitarResponse toBassGuitarResponse(BassGuitarDto bassGuitarDto) {
		return new BassGuitarResponse(bassGuitarDto);
	}

	public static AcousticAndClassicGuitarResponse toAcousticAndClassicGuitarResponse(
		AcousticAndClassicGuitarDto acousticAndClassicGuitarDto
	) {
		return new AcousticAndClassicGuitarResponse(acousticAndClassicGuitarDto);
	}

	public static EffectorResponse toEffectorResponse(EffectorDto effectorDto) {
		return new EffectorResponse(effectorDto);
	}

	public static AmplifierResponse toAmplifierResponse(AmplifierDto amplifierDto) {
		return new AmplifierResponse(amplifierDto);
	}

	public static AudioEquipmentResponse toAudioEquipmentResponse(AudioEquipmentDto audioEquipmentDto) {
		return new AudioEquipmentResponse(audioEquipmentDto);
	}
}
