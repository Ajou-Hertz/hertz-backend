package com.ajou.hertz.domain.instrument.effector.constant;

import static com.ajou.hertz.domain.instrument.effector.constant.EffectorType.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EffectorFeature {

	GUITAR_WAH(GUITAR, "와우"),
	GUITAR_EQ(GUITAR, "Eq"),
	GUITAR_VOLUME(GUITAR, "볼륨"),
	GUITAR_COMPRESSOR(GUITAR, "컴프레서"),
	GUITAR_OVER(GUITAR, "오버"),
	GUITAR_DISTORTION(GUITAR, "디스토션"),
	GUITAR_BOOST(GUITAR, "부스트"),
	GUITAR_SPATIOTEMPORAL_EFFECT(GUITAR, "공간계"),
	GUITAR_MODULATION(GUITAR, "모듈레이션"),
	GUITAR_AMPLIFIER_SIMULATOR(GUITAR, "앰프시뮬"),
	GUITAR_MULTI(GUITAR, "멀티"),
	GUITAR_BOARD_PARTS(GUITAR, "보드용부품"),

	BASS_COMPRESSOR(BASS, "컴프레서"),
	BASS_LIMITER(BASS, "리미터"),
	BASS_DRIVE(BASS, "드라이브"),

	MULTI_MULTI(MULTI, "멀티"),

	PEDAL_BOARD_BOARD(PEDAL_BOARD, "보드"),
	PEDAL_BOARD_POWER_SUPPLY(PEDAL_BOARD, "파워서플라이"),
	PEDAL_BOARD_BUFFER(PEDAL_BOARD, "버퍼"),
	PEDAL_BOARD_PARALLEL_MIXER(PEDAL_BOARD, "병렬 믹서"),

	ETC(null, "그 외");

	private final EffectorType effectorType;
	private final String description;
}
