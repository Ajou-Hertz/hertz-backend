package com.ajou.hertz.domain.instrument.constant;

import static org.springframework.data.domain.Sort.Direction.*;

import org.springframework.data.domain.Sort;

public enum InstrumentSortOption {

	CREATED_BY_DESC;

	public Sort toSort() {
		return switch (this) {
			case CREATED_BY_DESC -> Sort.by(DESC, "createdAt");
		};
	}
}
