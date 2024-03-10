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
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
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
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<Instrument> expectedResult = new PageImpl<>(List.of(
			createInstrument(1L, user),
			createInstrument(2L, user),
			createInstrument(3L, user)
		));
		given(instrumentRepository.findAll(any(Pageable.class))).willReturn(expectedResult);

		// when
		Page<InstrumentDto> actualResult = sut.findInstruments(page, pageSize, sort);

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
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<ElectricGuitar> expectedResult = new PageImpl<>(List.of(
			createElectricGuitar(1L, user),
			createElectricGuitar(2L, user),
			createElectricGuitar(3L, user)
		));
		given(instrumentRepository.findElectricGuitars(page, pageSize, sort)).willReturn(expectedResult);

		// when
		Page<ElectricGuitarDto> actualResult = sut.findElectricGuitars(page, pageSize, sort);

		// then
		then(instrumentRepository).should().findElectricGuitars(page, pageSize, sort);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(ElectricGuitar::getId).toList(),
			actualResult.getContent().stream().map(ElectricGuitarDto::getId).toList()
		);
	}

	@Test
	void 베이스_기타_목록을_조회한다() throws Exception {
		// given
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<BassGuitar> expectedResult = new PageImpl<>(List.of(
			createBassGuitar(1L, user),
			createBassGuitar(2L, user),
			createBassGuitar(3L, user)
		));
		given(instrumentRepository.findBassGuitars(page, pageSize, sort)).willReturn(expectedResult);

		// when
		Page<BassGuitarDto> actualResult = sut.findBassGuitars(page, pageSize, sort);

		// then
		then(instrumentRepository).should().findBassGuitars(page, pageSize, sort);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(BassGuitar::getId).toList(),
			actualResult.getContent().stream().map(BassGuitarDto::getId).toList()
		);
	}

	@Test
	void 어쿠스틱_클래식_기타_목록을_조회한다() throws Exception {
		// given
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<AcousticAndClassicGuitar> expectedResult = new PageImpl<>(List.of(
			createAcousticAndClassicGuitar(1L, user),
			createAcousticAndClassicGuitar(2L, user),
			createAcousticAndClassicGuitar(3L, user)
		));
		given(instrumentRepository.findAcousticAndClassicGuitars(page, pageSize, sort)).willReturn(expectedResult);

		// when
		Page<AcousticAndClassicGuitarDto> actualResult = sut.findAcousticAndClassicGuitars(page, pageSize, sort);

		// then
		then(instrumentRepository).should().findAcousticAndClassicGuitars(page, pageSize, sort);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(AcousticAndClassicGuitar::getId).toList(),
			actualResult.getContent().stream().map(AcousticAndClassicGuitarDto::getId).toList()
		);
	}

	@Test
	void 이펙터_목록을_조회한다() throws Exception {
		// given
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<Effector> expectedResult = new PageImpl<>(List.of(
			createEffector(1L, user),
			createEffector(2L, user),
			createEffector(3L, user)
		));
		given(instrumentRepository.findEffectors(page, pageSize, sort)).willReturn(expectedResult);

		// when
		Page<EffectorDto> actualResult = sut.findEffectors(page, pageSize, sort);

		// then
		then(instrumentRepository).should().findEffectors(page, pageSize, sort);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(Effector::getId).toList(),
			actualResult.getContent().stream().map(EffectorDto::getId).toList()
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

	private BassGuitar createBassGuitar(long id, User seller) throws Exception {
		Constructor<BassGuitar> bassGuitarConstructor = BassGuitar.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		bassGuitarConstructor.setAccessible(true);
		return bassGuitarConstructor.newInstance(
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

	private AcousticAndClassicGuitar createAcousticAndClassicGuitar(long id, User seller) throws Exception {
		Constructor<AcousticAndClassicGuitar> acousticAndClassicGuitarConstructor =
			AcousticAndClassicGuitar.class.getDeclaredConstructor(
				Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
				Short.class, Integer.class, Boolean.class, String.class,
				AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
				AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
			);
		acousticAndClassicGuitarConstructor.setAccessible(true);
		return acousticAndClassicGuitarConstructor.newInstance(
			id,
			seller,
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			AcousticAndClassicGuitarBrand.HEX,
			AcousticAndClassicGuitarModel.JUMBO_BODY,
			AcousticAndClassicGuitarWood.PLYWOOD,
			AcousticAndClassicGuitarPickUp.MICROPHONE
		);
	}

	private Effector createEffector(long id, User seller) throws Exception {
		Constructor<Effector> effectorConstructor = Effector.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			EffectorType.class, EffectorFeature.class
		);
		effectorConstructor.setAccessible(true);
		return effectorConstructor.newInstance(
			id,
			seller,
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			EffectorType.GUITAR,
			EffectorFeature.ETC
		);
	}

	private User createUser() throws Exception {
		return createUser(1L);
	}
}
