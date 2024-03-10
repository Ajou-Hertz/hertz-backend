package com.ajou.hertz.domain.instrument.dto.request;

import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter    // for @ModelAttribute
@Getter
public class InstrumentFilterConditions {

	private InstrumentProgressStatus progress;
}
