package com.ajou.hertz.unit.common.entity.full_address;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ajou.hertz.common.entity.FullAddress;
import com.ajou.hertz.common.exception.InvalidAddressFormatException;

@DisplayName("[Unit] Entity - FullAddress")
public class FullAddressParsingTest {

	@ParameterizedTest
	@MethodSource("fullAddressesParsingTest")
	void 전체_주소를_파싱하고_결과와_일치하는지_확인한다(
		String fullAddress,
		String detailAddress,
		String expectedSido,
		String expectedSgg,
		String expectedLotNumberAddress,
		String expectedRoadAddress,
		String expectedDetailAddress
	) {
		FullAddress parsedAddress = FullAddress.of(fullAddress, detailAddress);
		assertThat(parsedAddress.getSido()).isEqualTo(expectedSido);
		assertThat(parsedAddress.getSgg()).isEqualTo(expectedSgg);
		assertThat(parsedAddress.getLotNumberAddress()).isEqualTo(expectedLotNumberAddress);
		assertThat(parsedAddress.getRoadAddress()).isEqualTo(expectedRoadAddress);
		assertThat(parsedAddress.getDetailAddress()).isEqualTo(expectedDetailAddress);
	}

	private static Stream<Arguments> fullAddressesParsingTest() {
		return Stream.of(
			Arguments.of("경기 성남시 분당구 판교역로 152", "12층", "경기", "성남시 분당구", null, "판교역로 152", "12층"),
			Arguments.of("경북 안동시 일직면 감시골길 63", "빌라 202호", "경북", "안동시", null, "일직면 감시골길 63", "빌라 202호"),
			Arguments.of("경기 여주시 가남읍 가남로 15", "아파트 101호", "경기", "여주시", null, "가남읍 가남로 15", "아파트 101호"),
			Arguments.of("제주특별자치도 제주시 가령골길 1", "빌라 202호", "제주특별자치도", "제주시", null, "가령골길 1", "빌라 202호"),
			Arguments.of("인천 서구 가남로 51", "아파트 101호", "인천", "서구", null, "가남로 51", "아파트 101호"),
			Arguments.of("강원특별자치도 원주시 지정면 신평리 469", "빌라 202호", "강원특별자치도", "원주시", "지정면 신평리 469", null, "빌라 202호"),
			Arguments.of("전남 고흥군 고흥읍 등암리 1679", "아파트 101호", "전남", "고흥군", "고흥읍 등암리 1679", null, "아파트 101호"),
			Arguments.of("충북 청주시 서원구 모충동 372", "빌라 202호", "충북", "청주시 서원구", "모충동 372", null, "빌라 202호"),
			Arguments.of("인천 서구", "빌라 202호", "인천", "서구", null, null, "빌라 202호")
		);
	}

	@Test
	void 기본_생성자의_작동을_확인한다() throws Exception {
		Constructor<FullAddress> constructor = FullAddress.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		FullAddress fullAddress = constructor.newInstance();
		assertNotNull(fullAddress);
	}

	@Test
	void 잘못된_형식의_주소이면_예외가_발생하는지_확인한다() {
		assertThrows(InvalidAddressFormatException.class, () -> FullAddress.of("전라북도", "상세주소"));
	}
}
