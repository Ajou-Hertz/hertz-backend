package com.ajou.hertz.domain.instrument.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for multipart/form-data with @ModelAttribute
@Getter
public class CreateNewAmplifierRequest extends CreateNewInstrumentRequest<Amplifier> {

	@NotNull
	private AmplifierType type;

	@NotNull
	private AmplifierBrand brand;

	@NotNull
	private AmplifierUsage usage;

	private CreateNewAmplifierRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) {
		super(title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags);
		this.type = type;
		this.brand = brand;
		this.usage = usage;
	}

	public Amplifier toEntity(User seller) {
		return Amplifier.create(
			seller,
			getTitle(),
			getProgressStatus(),
			getTradeAddress().toEntity(),
			getQualityStatus(),
			getPrice(),
			getHasAnomaly(),
			getDescription(),
			getType(),
			getBrand(),
			getUsage()
		);
	}
}
