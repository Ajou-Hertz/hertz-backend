package com.ajou.hertz.integration.domain.instrument.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import com.ajou.hertz.common.config.JpaConfig;
import com.ajou.hertz.common.config.QuerydslConfig;
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
import com.ajou.hertz.domain.instrument.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.repository.UserRepository;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Integration] Repository - Instrument")
@ActiveProfiles("test")
@Import({QuerydslConfig.class, JpaConfig.class})
@DataJpaTest
class InstrumentRepositoryTest {

	private final InstrumentRepository sut;
	private final UserRepository userRepository;

	@Autowired
	public InstrumentRepositoryTest(
		InstrumentRepository instrumentRepository,
		UserRepository userRepository
	) {
		this.sut = instrumentRepository;
		this.userRepository = userRepository;
	}

	@Test
	void 일렉_기타_목록을_조회한다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		ElectricGuitarFilterConditions filterConditions = createEmptyElectricGuitarFilterConditions();
		User user = userRepository.save(createUser());
		List<Instrument> savedInstruments = sut.saveAll(List.of(
			createBassGuitar(user),
			createElectricGuitar(user),
			createElectricGuitar(user)
		));

		// when
		Page<ElectricGuitar> result = sut.findElectricGuitars(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(savedInstruments.size() - 1);
	}

	@Test
	void 베이스_기타_목록을_조회한다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		BassGuitarFilterConditions filterConditions = createEmptyBassGuitarFilterConditions();
		User user = userRepository.save(createUser());
		List<Instrument> savedInstruments = sut.saveAll(List.of(
			createElectricGuitar(user),
			createBassGuitar(user),
			createBassGuitar(user)
		));

		// when
		Page<BassGuitar> result = sut.findBassGuitars(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(savedInstruments.size() - 1);
	}

	@Test
	void 어쿠스틱_클래식_기타_목록을_조회한다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		AcousticAndClassicGuitarFilterConditions filterConditions =
			createEmptyAcousticAndClassicGuitarFilterConditions();
		User user = userRepository.save(createUser());
		List<Instrument> savedInstruments = sut.saveAll(List.of(
			createElectricGuitar(user),
			createAcousticAndClassicGuitar(user),
			createAcousticAndClassicGuitar(user)
		));

		// when
		Page<AcousticAndClassicGuitar> result = sut.findAcousticAndClassicGuitars(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(savedInstruments.size() - 1);
	}

	@Test
	void 이펙터_목록을_조회한다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		EffectorFilterConditions filterConditions = createEmptyEffectorFilterConditions();
		User user = userRepository.save(createUser());
		List<Instrument> savedInstruments = sut.saveAll(List.of(
			createElectricGuitar(user),
			createEffector(user),
			createEffector(user)
		));

		// when
		Page<Effector> result = sut.findEffectors(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(savedInstruments.size() - 1);
	}

	@Test
	void 앰프_목록을_조회한다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		AmplifierFilterConditions filterConditions = createEmptyAmplifierFilterConditions();
		User user = userRepository.save(createUser());
		List<Instrument> savedInstruments = sut.saveAll(List.of(
			createElectricGuitar(user),
			createAmplifier(user),
			createAmplifier(user)
		));

		// when
		Page<Amplifier> result = sut.findAmplifiers(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(savedInstruments.size() - 1);
	}

	@Test
	void 음향_장비_목록을_조회한다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_ASC;
		AudioEquipmentFilterConditions filterConditions = createAudioEquipmentFilterConditions();
		User user = userRepository.save(createUser());
		List<Instrument> savedInstruments = sut.saveAll(List.of(
			createElectricGuitar(user),
			createAudioEquipment(user),
			createAudioEquipment(user)
		));

		// when
		Page<AudioEquipment> result = sut.findAudioEquipments(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(savedInstruments.size() - 1);
	}

	private Address createAddress() {
		return new Address("서울특별시", "강남구", "청담동");
	}

	private User createUser() throws Exception {
		return ReflectionUtils.createUser(
			null,
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

	private ElectricGuitar createElectricGuitar(User seller) throws Exception {
		return ReflectionUtils.createElectricGuitar(
			null,
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

	private BassGuitar createBassGuitar(User seller) throws Exception {
		return ReflectionUtils.createBassGuitar(
			null,
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

	private AcousticAndClassicGuitar createAcousticAndClassicGuitar(User seller) throws Exception {
		return ReflectionUtils.createAcousticAndClassicGuitar(
			null,
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

	private Effector createEffector(User seller) throws Exception {
		return ReflectionUtils.createEffector(
			null,
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

	private Amplifier createAmplifier(User seller) throws Exception {
		return ReflectionUtils.createAmplifier(
			null,
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

	private AudioEquipment createAudioEquipment(User seller) throws Exception {
		return ReflectionUtils.createAudioEquipment(
			null,
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

	private ElectricGuitarFilterConditions createEmptyElectricGuitarFilterConditions() throws Exception {
		return ReflectionUtils.createElectricGuitarFilterConditions(null, null, null, null, null, null);
	}

	private BassGuitarFilterConditions createEmptyBassGuitarFilterConditions() throws Exception {
		return ReflectionUtils.createBassGuitarFilterConditions(null, null, null, null, null, null, null);
	}

	private AcousticAndClassicGuitarFilterConditions createEmptyAcousticAndClassicGuitarFilterConditions(
	) throws Exception {
		return ReflectionUtils.createAcousticAndClassicGuitarFilterConditions(null, null, null, null, null, null, null);
	}

	private EffectorFilterConditions createEmptyEffectorFilterConditions() throws Exception {
		return ReflectionUtils.createEffectorFilterConditions(null, null, null, null, null);
	}

	private AmplifierFilterConditions createEmptyAmplifierFilterConditions() throws Exception {
		return ReflectionUtils.createAmplifierFilterConditions(null, null, null, null, null, null);
	}

	private AudioEquipmentFilterConditions createAudioEquipmentFilterConditions() throws Exception {
		return ReflectionUtils.createAudioEquipmentFilterConditions(null, null, null, null);
	}
}
