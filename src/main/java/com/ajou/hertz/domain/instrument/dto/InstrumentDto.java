package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
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

	private Long id;
	private UserDto seller;
	private String title;
	private InstrumentProgressStatus progressStatus;
	private AddressDto tradeAddress;
	private Short qualityStatus;
	private Integer price;
	private Boolean hasAnomaly;
	private String description;
	private List<InstrumentImageDto> images;
	private List<String> hashtags;

	public static InstrumentDto from(Instrument instrument) {
		return new InstrumentDto(
			instrument.getId(),
			UserDto.from(instrument.getSeller()),
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
}
