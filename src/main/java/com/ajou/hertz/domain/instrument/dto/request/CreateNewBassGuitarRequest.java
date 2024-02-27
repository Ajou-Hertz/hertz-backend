package com.ajou.hertz.domain.instrument.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for multipart/form-data with @ModelAttribute
@Getter
public class CreateNewBassGuitarRequest extends CreateNewInstrumentRequest<BassGuitar> {

	@NotNull
	private BassGuitarBrand brand;

	@NotNull
	private BassGuitarPickUp pickUp;

	@NotNull
	private BassGuitarPreAmplifier preAmplifier;

	@NotNull
	private GuitarColor color;

	private CreateNewBassGuitarRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) {
		super(title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags);
		this.brand = brand;
		this.pickUp = pickUp;
		this.preAmplifier = preAmplifier;
		this.color = color;
	}

	public BassGuitar toEntity(User seller) {
		return BassGuitar.create(
			seller,
			getTitle(),
			getProgressStatus(),
			getTradeAddress().toEntity(),
			getQualityStatus(),
			getPrice(),
			getHasAnomaly(),
			getDescription(),
			getBrand(),
			getPickUp(),
			getPreAmplifier(),
			getColor()
		);
	}
}
