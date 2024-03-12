package com.ajou.hertz.unit.domain.instrument.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.constant.AudioEquipmentType;
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
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.util.ReflectionUtils;

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
		ElectricGuitarFilterConditions filterConditions = createElectricGuitarFilterConditions();
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<ElectricGuitar> expectedResult = new PageImpl<>(List.of(
			createElectricGuitar(1L, user),
			createElectricGuitar(2L, user),
			createElectricGuitar(3L, user)
		));
		given(instrumentRepository.findElectricGuitars(page, pageSize, sort, filterConditions))
			.willReturn(expectedResult);

		// when
		Page<ElectricGuitarDto> actualResult = sut.findElectricGuitars(page, pageSize, sort, filterConditions);

		// then
		then(instrumentRepository).should().findElectricGuitars(page, pageSize, sort, filterConditions);
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
		BassGuitarFilterConditions filterConditions = createBassGuitarFilterConditions();
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<BassGuitar> expectedResult = new PageImpl<>(List.of(
			createBassGuitar(1L, user),
			createBassGuitar(2L, user),
			createBassGuitar(3L, user)
		));
		given(instrumentRepository.findBassGuitars(page, pageSize, sort, filterConditions)).willReturn(expectedResult);

		// when
		Page<BassGuitarDto> actualResult = sut.findBassGuitars(page, pageSize, sort, filterConditions);

		// then
		then(instrumentRepository).should().findBassGuitars(page, pageSize, sort, filterConditions);
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
		AcousticAndClassicGuitarFilterConditions filterConditions =
			createEmptyAcousticAndClassicGuitarFilterConditions();
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<AcousticAndClassicGuitar> expectedResult = new PageImpl<>(List.of(
			createAcousticAndClassicGuitar(1L, user),
			createAcousticAndClassicGuitar(2L, user),
			createAcousticAndClassicGuitar(3L, user)
		));
		given(instrumentRepository.findAcousticAndClassicGuitars(page, pageSize, sort, filterConditions))
			.willReturn(expectedResult);

		// when
		Page<AcousticAndClassicGuitarDto> actualResult =
			sut.findAcousticAndClassicGuitars(page, pageSize, sort, filterConditions);

		// then
		then(instrumentRepository).should().findAcousticAndClassicGuitars(page, pageSize, sort, filterConditions);
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
		EffectorFilterConditions filterConditions = createEmptyEffectorFilterConditions();
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<Effector> expectedResult = new PageImpl<>(List.of(
			createEffector(1L, user),
			createEffector(2L, user),
			createEffector(3L, user)
		));
		given(instrumentRepository.findEffectors(page, pageSize, sort, filterConditions)).willReturn(expectedResult);

		// when
		Page<EffectorDto> actualResult = sut.findEffectors(page, pageSize, sort, filterConditions);

		// then
		then(instrumentRepository).should().findEffectors(page, pageSize, sort, filterConditions);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(Effector::getId).toList(),
			actualResult.getContent().stream().map(EffectorDto::getId).toList()
		);
	}

	@Test
	void 앰프_목록을_조회한다() throws Exception {
		// given
		int page = 0;
		int pageSize = 10;
		AmplifierFilterConditions filterConditions = createAmplifierFilterConditions();
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<Amplifier> expectedResult = new PageImpl<>(List.of(
			createAmplifier(1L, user),
			createAmplifier(2L, user),
			createAmplifier(3L, user)
		));
		given(instrumentRepository.findAmplifiers(page, pageSize, sort, filterConditions)).willReturn(expectedResult);

		// when
		Page<AmplifierDto> actualResult = sut.findAmplifiers(page, pageSize, sort, filterConditions);

		// then
		then(instrumentRepository).should().findAmplifiers(page, pageSize, sort, filterConditions);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(Amplifier::getId).toList(),
			actualResult.getContent().stream().map(AmplifierDto::getId).toList()
		);
	}

	@Test
	void 음향_장비_목록을_조회한다() throws Exception {
		// given
		int page = 0;
		int pageSize = 10;
		AudioEquipmentFilterConditions filterConditions = createAudioEquipmentFilterConditions();
		InstrumentSortOption sort = InstrumentSortOption.CREATED_BY_DESC;
		User user = createUser();
		Page<AudioEquipment> expectedResult = new PageImpl<>(List.of(
			createAudioEquipment(1L, user),
			createAudioEquipment(2L, user),
			createAudioEquipment(3L, user)
		));
		given(instrumentRepository.findAudioEquipments(page, pageSize, sort, filterConditions))
			.willReturn(expectedResult);

		// when
		Page<AudioEquipmentDto> actualResult = sut.findAudioEquipments(page, pageSize, sort, filterConditions);

		// then
		then(instrumentRepository).should().findAudioEquipments(page, pageSize, sort, filterConditions);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.getNumberOfElements()).isEqualTo(actualResult.getNumberOfElements());
		assertIterableEquals(
			expectedResult.getContent().stream().map(AudioEquipment::getId).toList(),
			actualResult.getContent().stream().map(AudioEquipmentDto::getId).toList()
		);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentRepository).shouldHaveNoMoreInteractions();
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

	private Instrument createInstrument(long instrumentId, User seller) throws Exception {
		return ReflectionUtils.createBassGuitar(
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
		return ReflectionUtils.createElectricGuitar(
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

	private AcousticAndClassicGuitar createAcousticAndClassicGuitar(long id, User seller) throws Exception {
		return ReflectionUtils.createAcousticAndClassicGuitar(
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
		return ReflectionUtils.createEffector(
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

	private Amplifier createAmplifier(long id, User seller) throws Exception {
		return ReflectionUtils.createAmplifier(
			id,
			seller,
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			AmplifierType.GUITAR,
			AmplifierBrand.FENDER,
			AmplifierUsage.HOME
		);
	}

	private AudioEquipment createAudioEquipment(long id, User seller) throws Exception {
		return ReflectionUtils.createAudioEquipment(
			id,
			seller,
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			AudioEquipmentType.AUDIO_EQUIPMENT
		);
	}

	private User createUser() throws Exception {
		return createUser(1L);
	}

	private InstrumentFilterConditions createEmptyInstrumentFilterConditions() throws Exception {
		return ReflectionUtils.createInstrumentFilterConditions(null, null, null);
	}

	private ElectricGuitarFilterConditions createElectricGuitarFilterConditions() throws Exception {
		return ReflectionUtils.createElectricGuitarFilterConditions(
			InstrumentProgressStatus.SELLING,
			"서울특별시",
			"종로구",
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			GuitarColor.RED
		);
	}

	private BassGuitarFilterConditions createBassGuitarFilterConditions() throws Exception {
		return ReflectionUtils.createBassGuitarFilterConditions(
			InstrumentProgressStatus.SELLING,
			"서울특별시",
			"종로구",
			BassGuitarBrand.FENDER,
			BassGuitarPickUp.JAZZ,
			BassGuitarPreAmplifier.ACTIVE,
			GuitarColor.RED
		);
	}

	private AcousticAndClassicGuitarFilterConditions createEmptyAcousticAndClassicGuitarFilterConditions(
	) throws Exception {
		return ReflectionUtils.createAcousticAndClassicGuitarFilterConditions(
			InstrumentProgressStatus.RESERVED,
			"서울특별시",
			"종로구",
			AcousticAndClassicGuitarBrand.BENTIVOGLIO,
			AcousticAndClassicGuitarModel.DREADNOUGHT,
			AcousticAndClassicGuitarWood.PLYWOOD_AND_SOLID_WOOD,
			AcousticAndClassicGuitarPickUp.MAGNETIC
		);
	}

	private EffectorFilterConditions createEmptyEffectorFilterConditions() throws Exception {
		return ReflectionUtils.createEffectorFilterConditions(
			InstrumentProgressStatus.SOLD_OUT,
			"서울특별시",
			"종로구",
			EffectorType.GUITAR,
			EffectorFeature.ETC
		);
	}

	private AmplifierFilterConditions createAmplifierFilterConditions() throws Exception {
		return ReflectionUtils.createAmplifierFilterConditions(
			InstrumentProgressStatus.SELLING,
			"서울특별시",
			"종로구",
			AmplifierType.GUITAR,
			AmplifierBrand.FENDER,
			AmplifierUsage.HOME
		);
	}

	private AudioEquipmentFilterConditions createAudioEquipmentFilterConditions() throws Exception {
		return ReflectionUtils.createAudioEquipmentFilterConditions(
			InstrumentProgressStatus.SELLING,
			"서울특별시",
			"종로구",
			AudioEquipmentType.AUDIO_EQUIPMENT
		);
	}
}
