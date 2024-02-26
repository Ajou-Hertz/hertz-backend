package com.ajou.hertz.domain.instrument.dto.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for multipart/form-data with @ModelAttribute
@Getter
public class CreateNewElectricGuitarRequest {

	@Schema(description = "제목", example = "펜더 로드원 텔레캐스터")
	@NotBlank
	private String title;

	@Schema(description = "악기 사진")
	@NotNull
	@Size(min = 4, max = 7)
	private List<MultipartFile> images;

	@NotNull
	private InstrumentProgressStatus progressStatus;

	@Schema(description = "거래 장소")
	@NotNull
	private AddressRequest tradeAddress;

	@Schema(description = "악기 상태. 1~5 단계로 구성됩니다.", example = "3")
	@NotNull
	@Min(1)
	@Max(5)
	private Short qualityStatus;

	@Schema(description = "가격", example = "527000")
	@NotNull
	private Integer price;

	@Schema(description = "특이사항 유무", example = "true")
	@NotNull
	private Boolean hasAnomaly;

	@Schema(description = "특이사항 및 상세 설명. 내용이 없을 경우에는 빈 문자열로 요청하면 됩니다.", example = "14년 시리얼 펜더 로드원 50 텔레입니다. 기존 ...")
	@NotNull
	private String description;

	@NotNull
	private ElectricGuitarBrand brand;

	@NotNull
	private ElectricGuitarModel model;

	@Schema(description = "생상연도", example = "2014")
	@NotNull
	private Short productionYear;

	@NotNull
	private GuitarColor color;

	@Schema(description = "해시태그(각 해시태그마다 최대 10글자)", example = "[\"펜더\", \"Fender\"]")
	private List<@NotBlank @Length(max = 10) String> hashtags;

	public ElectricGuitar toEntity(User seller) {
		return ElectricGuitar.create(
			seller,
			title,
			progressStatus,
			tradeAddress.toEntity(),
			qualityStatus,
			price,
			hasAnomaly,
			description,
			brand,
			model,
			productionYear,
			color
		);
	}
}
