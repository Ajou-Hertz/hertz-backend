package com.ajou.hertz.domain.instrument.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InstrumentProgressStatus {

	SELLING("판매중"),
	RESERVED("예약중"),
	SOLD_OUT("판매 완료");

	private final String description;
}
