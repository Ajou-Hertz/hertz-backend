package com.ajou.hertz.domain.instrument.amplifier.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AmplifierUsage {

	HOME("가정용"),
	PERFORMANCE("공연용");

	private final String description;
}
