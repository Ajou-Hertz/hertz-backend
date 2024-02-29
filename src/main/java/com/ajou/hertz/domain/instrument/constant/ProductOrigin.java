package com.ajou.hertz.domain.instrument.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductOrigin {

	DOMESTIC("국내"),
	FOREIGN("국외");

	private final String description;
}
