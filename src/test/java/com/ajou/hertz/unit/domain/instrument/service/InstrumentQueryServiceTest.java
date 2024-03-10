package com.ajou.hertz.unit.domain.instrument.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;

@DisplayName("[Unit] Service(Query) - Instrument")
@ExtendWith(MockitoExtension.class)
class InstrumentQueryServiceTest {

	@InjectMocks
	private InstrumentQueryService sut;

	@Mock
	private InstrumentRepository instrumentRepository;

	@Test
	void 종류_상관_없이_전체_악기_목록을_조회한다() throws Exception {
		// given
		User user = createUser();
		Page<Instrument> expectedResult = new PageImpl<>(List.of(
			createInstrument(1L, user),
			createInstrument(2L, user),
			createInstrument(3L, user)
		));
		given(instrumentRepository.findAll(any(Pageable.class))).willReturn(expectedResult);

		// when
		Page<InstrumentDto> actualResult = sut.findInstruments(Pageable.ofSize(10));

		// then
		then(instrumentRepository).should().findAll(any(Pageable.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(Instrument::getId).toList(),
			actualResult.getContent().stream().map(InstrumentDto::getId).toList()
		);
	}

	@Test
	void 일렉_기타_목록을_조회한다() throws Exception {
		// given
		User user = createUser();
		Page<ElectricGuitar> expectedResult = new PageImpl<>(List.of(
			createElectricGuitar(1L, user),
			createElectricGuitar(2L, user),
			createElectricGuitar(3L, user)
		));
		given(instrumentRepository.findElectricGuitars(any(Pageable.class))).willReturn(expectedResult);

		// when
		Page<ElectricGuitarDto> actualResult = sut.findElectricGuitars(Pageable.ofSize(10));

		// then
		then(instrumentRepository).should().findElectricGuitars(any(Pageable.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(ElectricGuitar::getId).toList(),
			actualResult.getContent().stream().map(ElectricGuitarDto::getId).toList()
		);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentRepository).shouldHaveNoMoreInteractions();
	}

	private Address createAddress() {
		return new Address("서울특별시", "강남구", "청담동");
	}

	private Instrument createInstrument(long instrumentId, User seller) throws Exception {
		Constructor<BassGuitar> bassGuitarConstructor = BassGuitar.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		bassGuitarConstructor.setAccessible(true);
		return bassGuitarConstructor.newInstance(
			instrumentId,
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

	private ElectricGuitar createElectricGuitar(long id, User seller) throws Exception {
		Constructor<ElectricGuitar> electricGuitarConstructor = ElectricGuitar.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class, Short.class,
			Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class, ElectricGuitarModel.class,
			Short.class, GuitarColor.class
		);
		electricGuitarConstructor.setAccessible(true);
		return electricGuitarConstructor.newInstance(
			id,
			seller,
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			(short)2014,
			GuitarColor.RED
		);
	}

	private User createUser(long id) throws Exception {
		Constructor<User> userConstructor = User.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			String.class, LocalDate.class, Gender.class, String.class, String.class
		);
		userConstructor.setAccessible(true);
		return userConstructor.newInstance(
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

	private User createUser() throws Exception {
		return createUser(1L);
	}
}
