package com.ajou.hertz.domain.instrument.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for multipart/form-data with @ModelAttribute
@Getter
public class CreateNewElectricGuitarRequest extends CreateNewInstrumentRequest<ElectricGuitar> {

	@NotNull
	private ElectricGuitarBrand brand;

	@NotNull
	private ElectricGuitarModel model;

	@Schema(description = "생상연도", example = "2014")
	@NotNull
	private Short productionYear;

	@NotNull
	private GuitarColor color;

	private CreateNewElectricGuitarRequest(
		String title,
		List<MultipartFile> images,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color,
		List<String> hashtags
	) {
		super(title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.color = color;
	}

	public ElectricGuitar toEntity(User seller) {
		return ElectricGuitar.create(
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
			getProductionYear(),
			getColor()
		);
	}
}
