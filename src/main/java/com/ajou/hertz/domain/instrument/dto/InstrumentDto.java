package com.ajou.hertz.domain.instrument.dto;

import java.util.List;
import java.util.Map;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InstrumentDto {

	private static final Map<Class<? extends Instrument>, InstrumentCategory> instrumentCategoryMap = Map.of(
		ElectricGuitar.class, InstrumentCategory.ELECTRIC_GUITAR,
		BassGuitar.class, InstrumentCategory.BASS_GUITAR,
		AcousticAndClassicGuitar.class, InstrumentCategory.ACOUSTIC_AND_CLASSIC_GUITAR,
		Effector.class, InstrumentCategory.EFFECTOR,
		Amplifier.class, InstrumentCategory.AMPLIFIER,
		AudioEquipment.class, InstrumentCategory.AUDIO_EQUIPMENT
	);

	private Long id;
	private UserDto seller;
	private InstrumentCategory category;
	private String title;
	private InstrumentProgressStatus progressStatus;
	private AddressDto tradeAddress;
	private Short qualityStatus;
	private Integer price;
	private Boolean hasAnomaly;
	private String description;
	private List<InstrumentImageDto> images;
	private List<String> hashtags;

	protected InstrumentDto(Instrument instrument) {
		this(
			instrument.getId(),
			UserDto.from(instrument.getSeller()),
			getInstrumentCategory(instrument.getClass()),
			instrument.getTitle(),
			instrument.getProgressStatus(),
			AddressDto.from(instrument.getTradeAddress()),
			instrument.getQualityStatus(),
			instrument.getPrice(),
			instrument.getHasAnomaly(),
			instrument.getDescription(),
			instrument.getImages().toDtos(),
			instrument.getHashtags().toStrings()
		);
	}

	private static InstrumentCategory getInstrumentCategory(Class<? extends Instrument> instrumentClassType) {
		if (!instrumentCategoryMap.containsKey(instrumentClassType)) {
			throw new IllegalArgumentException("Unsupported instrument class: " + instrumentClassType.getName());
		}
		return instrumentCategoryMap.get(instrumentClassType);
	}
}
