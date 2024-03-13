package com.ajou.hertz.domain.instrument.dto.response;

import java.util.List;

import com.ajou.hertz.common.dto.response.AddressResponse;
import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EffectorResponse extends InstrumentResponse {

	@Schema(description = "이펙터 종류")
	private EffectorType type;

	@Schema(description = "이펙터 기능")
	private EffectorFeature feature;

	private EffectorResponse(
		Long id,
		Long sellerId,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressResponse tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageResponse> images,
		List<String> hashtags,
		EffectorType type,
		EffectorFeature feature
	) {
		super(
			id, sellerId, InstrumentCategory.EFFECTOR, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags
		);
		this.type = type;
		this.feature = feature;
	}

	private EffectorResponse(EffectorDto effectorDto) {
		super(effectorDto);
		this.type = effectorDto.getType();
		this.feature = effectorDto.getFeature();
	}

	public static EffectorResponse from(EffectorDto effectorDto) {
		return new EffectorResponse(effectorDto);
	}
}
