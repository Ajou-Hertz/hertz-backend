package com.ajou.hertz.domain.instrument.dto.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public abstract class InstrumentUpdateRequest {

	@Schema(description = "제목", example = "펜더 로드원 텔레캐스터")
	@Nullable
	private String title;

	@Nullable
	private InstrumentProgressStatus progressStatus;

	@Schema(description = "거래 장소")
	@Nullable
	private AddressRequest tradeAddress;

	@Schema(description = "악기 상태. 1~5 단계로 구성됩니다.", example = "3")
	@Min(1)
	@Max(5)
	@Nullable
	private Short qualityStatus;

	@Schema(description = "가격", example = "527000")
	@Nullable
	private Integer price;

	@Schema(description = "특이사항 유무", example = "true")
	@Nullable
	private Boolean hasAnomaly;

	@Schema(description = "특이사항 및 상세 설명. 내용이 없을 경우에는 빈 문자열로 요청하면 됩니다.", example = "14년 시리얼 펜더 로드원 50 텔레입니다. 기존 ...")
	@Nullable
	private String description;

	@Schema(description = "삭제한 이미지의 id 리스트")
	@Nullable
	private List<Long> deletedImageIds;

	@Schema(description = "새로 추가된 악기 이미지 리스트")
	@Nullable
	private List<MultipartFile> newImages;

	@Schema(description = "삭제한 해시태그의 id 리스트")
	@Nullable
	private List<Long> deletedHashtagIds;

	@Schema(description = "새로 추가된 악기 해시태그(각 해시태그마다 최대 10글자) 리스트", example = "[\"펜더\", \"Fender\"]")
	@Nullable
	private List<@NotBlank @Length(max = 10) String> newHashtags;
}
