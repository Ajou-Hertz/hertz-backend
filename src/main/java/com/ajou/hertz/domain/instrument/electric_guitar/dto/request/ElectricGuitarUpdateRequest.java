package com.ajou.hertz.domain.instrument.electric_guitar.dto.request;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentUpdateRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class ElectricGuitarUpdateRequest extends InstrumentUpdateRequest {

	@Nullable
	private ElectricGuitarBrand brand;

	@Nullable
	private ElectricGuitarModel model;

	@Schema(description = "생산 연도", example = "2014")
	@Nullable
	private Short productionYear;

	@Nullable
	private GuitarColor color;

	private ElectricGuitarUpdateRequest(
		@Nullable String title,
		@Nullable InstrumentProgressStatus progressStatus,
		@Nullable AddressRequest tradeAddress,
		@Nullable Short qualityStatus,
		@Nullable Integer price,
		@Nullable Boolean hasAnomaly,
		@Nullable String description,
		@Nullable List<Long> deletedImageIds,
		@Nullable List<MultipartFile> newImages,
		@Nullable List<Long> deletedHashtagIds,
		@Nullable List<String> newHashtags,
		@Nullable ElectricGuitarBrand brand,
		@Nullable ElectricGuitarModel model,
		@Nullable Short productionYear,
		@Nullable GuitarColor color
	) {
		super(title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			deletedImageIds, newImages, deletedHashtagIds, newHashtags);
		this.brand = brand;
		this.model = model;
		this.productionYear = productionYear;
		this.color = color;
	}
}
