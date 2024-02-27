package com.ajou.hertz.domain.instrument.dto;

import java.util.List;

import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.user.dto.UserDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ElectricGuitarDto {

	private Long id;
	private UserDto seller;
	private String title;
	private InstrumentProgressStatus progressStatus;
	private AddressDto tradeAddress;
	private Short qualityStatus;
	private Integer price;
	private Boolean hasAnomaly;
	private String description;
	private ElectricGuitarBrand brand;
	private ElectricGuitarModel model;
	private Short productionYear;
	private GuitarColor color;
	private List<InstrumentImageDto> images;
	private List<String> hashtags;

	public static ElectricGuitarDto from(ElectricGuitar electricGuitar) {
		return new ElectricGuitarDto(
			electricGuitar.getId(),
			UserDto.from(electricGuitar.getSeller()),
			electricGuitar.getTitle(),
			electricGuitar.getProgressStatus(),
			AddressDto.from(electricGuitar.getTradeAddress()),
			electricGuitar.getQualityStatus(),
			electricGuitar.getPrice(),
			electricGuitar.getHasAnomaly(),
			electricGuitar.getDescription(),
			electricGuitar.getBrand(),
			electricGuitar.getModel(),
			electricGuitar.getProductionYear(),
			electricGuitar.getColor(),
			electricGuitar.getImages().toDtos(),
			electricGuitar.getHashtags().toStrings()
		);
	}
}
