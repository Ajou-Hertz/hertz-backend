package com.ajou.hertz.integration.domain.instrument.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.amplifier.entity.Amplifier;
import com.ajou.hertz.domain.instrument.audio_equipment.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.audio_equipment.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.effector.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.effector.constant.EffectorType;
import com.ajou.hertz.domain.instrument.effector.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.effector.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
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
	void 주어진_id와_일치하는_일렉_기타를_단건_조회한다() throws Exception {
		// given
		User user = userRepository.save(createUser());
		Long electricGuitarId = sut.save(createElectricGuitar(user)).getId();

		// when
		Optional<ElectricGuitar> result = sut.findElectricGuitarById(electricGuitarId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(electricGuitarId);
	}

	@Test
	void 존재하지_않는_악기_id로_일렉_기타를_단건_조회하면_empty_optional_객체가_반환된다() {
		// given

		// when
		Optional<ElectricGuitar> result = sut.findElectricGuitarById(1L);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 주어진_id와_일치하는_베이스_기타를_단건_조회한다() throws Exception {
		// given
		User user = userRepository.save(createUser());
		Long bassGuitarId = sut.save(createBassGuitar(user)).getId();

		// when
		Optional<BassGuitar> result = sut.findBassGuitarById(bassGuitarId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(bassGuitarId);
	}

	@Test
	void 존재하지_않는_악기_id로_베이스_기타를_단건_조회하면_empty_optional_객체가_반환된다() {
		// given

		// when
		Optional<BassGuitar> result = sut.findBassGuitarById(1L);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 주어진_id와_일치하는_어쿠스틱_클래식_기타를_단건_조회한다() throws Exception {
		// given
		User user = userRepository.save(createUser());
		Long electricGuitarId = sut.save(createAcousticAndClassicGuitar(user)).getId();

		// when
		Optional<AcousticAndClassicGuitar> result = sut.findAcousticAndClassicGuitarById(electricGuitarId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(electricGuitarId);
	}

	@Test
	void 존재하지_않는_악기_id로_어쿠스틱_클래식_기타를_단건_조회하면_empty_optional_객체가_반환된다() {
		// given

		// when
		Optional<AcousticAndClassicGuitar> result = sut.findAcousticAndClassicGuitarById(1L);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 주어진_id와_일치하는_이펙터를_단건_조회한다() throws Exception {
		// given
		User user = userRepository.save(createUser());
		Long effectorId = sut.save(createEffector(user)).getId();

		// when
		Optional<Effector> result = sut.findEffectorById(effectorId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(effectorId);
	}

	@Test
	void 존재하지_않는_악기_id로_이펙터를_단건_조회하면_empty_optional_객체가_반환된다() {
		// given

		// when
		Optional<Effector> result = sut.findEffectorById(1L);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 주어진_id와_일치하는_앰프를_단건_조회한다() throws Exception {
		// given
		User user = userRepository.save(createUser());
		Long amplifierId = sut.save(createAmplifier(user)).getId();

		// when
		Optional<Amplifier> result = sut.findAmplifierById(amplifierId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(amplifierId);
	}

	@Test
	void 존재하지_않는_악기_id로_앰프를_단건_조회하면_empty_optional_객체가_반환된다() {
		// given

		// when
		Optional<Amplifier> result = sut.findAmplifierById(1L);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 주어진_id와_일치하는_음향_장비를_단건_조회한다() throws Exception {
		// given
		User user = userRepository.save(createUser());
		Long audioEquipmentId = sut.save(createAudioEquipment(user)).getId();

		// when
		Optional<AudioEquipment> result = sut.findAudioEquipmentById(audioEquipmentId);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(audioEquipmentId);
	}

	@Test
	void 존재하지_않는_악기_id로_음향_장비를_단건_조회하면_empty_optional_객체가_반환된다() {
		// given

		// when
		Optional<AudioEquipment> result = sut.findAudioEquipmentById(1L);

		// then
		assertThat(result).isEmpty();
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
	void 필터링_조건이_주어지고_일렉_기타_목록을_조회하면_조건에_일치하는_매물이_조회된다() throws Exception {
		// given
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		ElectricGuitarFilterConditions filterConditions = createElectricGuitarFilterConditions(
			InstrumentProgressStatus.SELLING,
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			GuitarColor.RED
		);
		User user = userRepository.save(createUser());
		sut.saveAll(List.of(
			createBassGuitar(user),
			createElectricGuitar(
				user,
				InstrumentProgressStatus.SELLING,
				ElectricGuitarBrand.FENDER_JAPAN,
				ElectricGuitarModel.TELECASTER,
				GuitarColor.BLACK
			),
			createElectricGuitar(
				user,
				InstrumentProgressStatus.SELLING,
				ElectricGuitarBrand.FENDER_USA,
				ElectricGuitarModel.TELECASTER,
				GuitarColor.RED
			)
		));

		// when
		Page<ElectricGuitar> result = sut.findElectricGuitars(0, 10, sortOption, filterConditions);

		// then
		assertThat(result.getNumberOfElements()).isEqualTo(1);
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

	private ElectricGuitar createElectricGuitar(
		User seller,
		InstrumentProgressStatus progressStatus,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		GuitarColor color
	) throws Exception {
		return ReflectionUtils.createElectricGuitar(
			null,
			seller,
			"Test electric guitar",
			progressStatus,
			createAddress(),
			(short)3,
			550000,
			true,
			"description",
			brand,
			model,
			(short)2014,
			color
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

	private ElectricGuitarFilterConditions createElectricGuitarFilterConditions(
		InstrumentProgressStatus progressStatus,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		GuitarColor color
	) throws Exception {
		return ReflectionUtils.createElectricGuitarFilterConditions(progressStatus, null, null, brand, model, color);
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
