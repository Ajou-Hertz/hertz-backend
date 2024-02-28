package com.ajou.hertz.domain.instrument.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for multipart/form-data with @ModelAttribute
@Getter
public class CreateNewEffectorRequest extends CreateNewInstrumentRequest<Effector> {

	@NotNull
	private EffectorType type;

	@NotNull
	private EffectorFeature feature;

	private CreateNewEffectorRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		EffectorType type,
		EffectorFeature feature
	) {
		super(title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags);
		this.type = type;
		this.feature = feature;
	}

	public Effector toEntity(User seller) {
		return Effector.create(
			seller,
			getTitle(),
			getProgressStatus(),
			getTradeAddress().toEntity(),
			getQualityStatus(),
			getPrice(),
			getHasAnomaly(),
			getDescription(),
			getType(),
			getFeature()
		);
	}
}
