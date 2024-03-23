package com.ajou.hertz.unit.domain.instrument.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentImageQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service(Query) - Instrument Image")
@ExtendWith(MockitoExtension.class)
class InstrumentImageQueryServiceTest {

	@InjectMocks
	private InstrumentImageQueryService sut;

	@Mock
	private InstrumentImageRepository instrumentImageRepository;

	@Test
	void 악기_id가_주어지고_해당_악기의_이미지를_전부_조회한다() throws Exception {
		// given
		long instrumentId = 2L;
		BassGuitar bassGuitar = createBassGuitar(instrumentId, createUser(1L));
		List<InstrumentImage> expectedResult = List.of(
			createInstrumentImage(3L, bassGuitar),
			createInstrumentImage(4L, bassGuitar),
			createInstrumentImage(5L, bassGuitar),
			createInstrumentImage(6L, bassGuitar)
		);
		given(instrumentImageRepository.findAllByInstrument_Id(instrumentId)).willReturn(expectedResult);

		// when
		List<InstrumentImage> actualResult = sut.findAllByInstrumentId(instrumentId);

		// then
		then(instrumentImageRepository).should().findAllByInstrument_Id(instrumentId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.size()).isEqualTo(expectedResult.size());
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentImageRepository).shouldHaveNoMoreInteractions();
	}

	private Address createAddress() {
		return new Address("서울특별시", "강남구", "청담동");
	}

	private User createUser(long id) throws Exception {
		return ReflectionUtils.createUser(
			id,
			Set.of(RoleType.USER),
			"test@mail.com",
			"password",
			"12345",
			"https://user-default-profile-image-url",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678",
			null
		);
	}

	private BassGuitar createBassGuitar(long id, User seller) throws Exception {
		return ReflectionUtils.createBassGuitar(
			id,
			seller,
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			BassGuitarBrand.FENDER,
			BassGuitarPickUp.JAZZ,
			BassGuitarPreAmplifier.ACTIVE,
			GuitarColor.RED
		);
	}

	private InstrumentImage createInstrumentImage(long id, Instrument instrument) throws Exception {
		return ReflectionUtils.createInstrumentImage(
			id,
			instrument,
			"original-image-name.png",
			"stored-image-name",
			"https://instrument-image"
		);
	}

}