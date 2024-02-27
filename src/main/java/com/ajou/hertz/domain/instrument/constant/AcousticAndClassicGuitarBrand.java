package com.ajou.hertz.domain.instrument.constant;

import static com.ajou.hertz.domain.instrument.constant.ProductOrigin.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AcousticAndClassicGuitarBrand {

	PRAYTON(DOMESTIC),
	CRAFTER(DOMESTIC),
	GOPHERWOOD(DOMESTIC),
	HEX(DOMESTIC),
	BENTIVOGLIO(DOMESTIC),
	PARKWOOD(DOMESTIC),
	ORANGEWOOD(DOMESTIC),
	CORT(DOMESTIC),
	MARTIN(FOREIGN),
	TAYLOR(FOREIGN),
	GIBSON(FOREIGN),
	SEAGULL(FOREIGN),
	EASTMAN(FOREIGN),
	SIGMA(FOREIGN),
	YAMAHA(FOREIGN),
	LAVA(FOREIGN),
	ETC(null);

	private final ProductOrigin origin;
}
