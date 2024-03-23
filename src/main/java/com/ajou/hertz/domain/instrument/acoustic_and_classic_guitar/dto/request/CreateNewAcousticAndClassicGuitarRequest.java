package com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewInstrumentRequest;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for multipart/form-data with @ModelAttribute
@Getter
public class CreateNewAcousticAndClassicGuitarRequest extends CreateNewInstrumentRequest<AcousticAndClassicGuitar> {

	@NotNull
	private AcousticAndClassicGuitarBrand brand;

	@NotNull
	private AcousticAndClassicGuitarModel model;

	@NotNull
	private AcousticAndClassicGuitarWood wood;

	@NotNull
	private AcousticAndClassicGuitarPickUp pickUp;

	private CreateNewAcousticAndClassicGuitarRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) {
		super(title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags);
		this.brand = brand;
		this.model = model;
		this.wood = wood;
		this.pickUp = pickUp;
	}

	public AcousticAndClassicGuitar toEntity(User seller) {
		return AcousticAndClassicGuitar.create(
			seller,
			getTitle(),
			getProgressStatus(),
			getTradeAddress().toEntity(),
			getQualityStatus(),
			getPrice(),
			getHasAnomaly(),
			getDescription(),
			getBrand(),
			getModel(),
			getWood(),
			getPickUp()
		);
	}
}
