package com.ajou.hertz.domain.instrument.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AcousticAndClassicGuitarPickUp {

	VIBRATION_SENSING("진동감지형"),
	MAGNETIC("마그네틱"),
	MICROPHONE("마이크");

	private final String description;
}
