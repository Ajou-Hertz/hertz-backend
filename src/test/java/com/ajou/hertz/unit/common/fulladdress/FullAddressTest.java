package com.ajou.hertz.unit.common.fulladdress;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ajou.hertz.common.entity.FullAddress;

public class FullAddressTest {

	@ParameterizedTest
	@MethodSource("FullAddressesParsingTest")
	void testAddressParsing(String fullAddress, String detailAddress, String expectedSido, String expectedSgg) {
		FullAddress parsedAddress = FullAddress.of(fullAddress, detailAddress);
		assertEquals(expectedSido, parsedAddress.getSido());
		assertEquals(expectedSgg, parsedAddress.getSgg());
	}

	private static Stream<Arguments> FullAddressesParsingTest() {
		return Stream.of(
			Arguments.of("경기 성남시 분당구 판교역로 152", "12층", "경기", "성남시 분당구"),
			Arguments.of("경북 안동시 일직면 감시골길 63", "빌라 202호", "경북", "안동시"),
			Arguments.of("경기 여주시 가남읍 가남로 15", "아파트 101호", "경기", "여주시"),
			Arguments.of("제주특별자치도 제주시 가령골길 1", "빌라 202호", "제주특별자치도", "제주시"),
			Arguments.of("인천 서구 가남로 51", "아파트 101호", "인천", "서구"),
			Arguments.of("강원특별자치도 원주시 지정면 신평리 469", "빌라 202호", "강원특별자치도", "원주시"),
			Arguments.of("전남 고흥군 고흥읍 등암리 1679", "아파트 101호", "전남", "고흥군"),
			Arguments.of("충북 청주시 서원구 모충동 372", "빌라 202호", "충북", "청주시 서원구"),
			Arguments.of("인천 서구 가좌동 157-21 ", "빌라 202호", "인천", "서구")
		);
	}
}
