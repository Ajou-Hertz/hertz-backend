package com.ajou.hertz.unit.domain.instrument.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.file.service.FileService;
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
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.service.UserQueryService;

@DisplayName("[Unit] Service(Command) - Instrument")
@ExtendWith(MockitoExtension.class)
class InstrumentCommandServiceTest {

	@InjectMocks
	private InstrumentCommandService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private FileService fileService;

	@Mock
	private InstrumentRepository instrumentRepository;

	@Mock
	private InstrumentHashtagRepository instrumentHashtagRepository;

	@Mock
	private InstrumentImageRepository instrumentImageRepository;

	@Test
	void 새로_등록할_일렉기타의_정보가_주어지면_일렉기타_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewElectricGuitarRequest electricGuitarRequest = createElectricGuitarRequest();
		User seller = createUser(1L);
		ElectricGuitar electricGuitar = createElectricGuitar(2L, seller);
		List<InstrumentImage> instrumentImages = List.of(createInstrumentImage(3L, electricGuitar));
		List<InstrumentHashtag> instrumentHashtags = List.of(createInstrumentHashtag(4L, electricGuitar));
		given(userQueryService.getById(sellerId)).willReturn(seller);
		given(instrumentRepository.save(any(Instrument.class))).willReturn(electricGuitar);
		given(fileService.uploadFiles(eq(electricGuitarRequest.getImages()), anyString()))
			.willReturn(List.of(createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		ElectricGuitarDto result = sut.createNewElectricGuitar(sellerId, electricGuitarRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(fileService).should().uploadFiles(eq(electricGuitarRequest.getImages()), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", electricGuitar.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	@Test
	void 베이스_기타의_정보가_주어지면_주어진_정보로_베이스_기타_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewBassGuitarRequest bassGuitarRequest = createBassGuitarRequest();
		User seller = createUser(1L);
		BassGuitar bassGuitar = createBassGuitar(2L, seller);
		List<InstrumentImage> instrumentImages = List.of(createInstrumentImage(3L, bassGuitar));
		List<InstrumentHashtag> instrumentHashtags = List.of(createInstrumentHashtag(4L, bassGuitar));
		given(userQueryService.getById(sellerId)).willReturn(seller);
		given(instrumentRepository.save(any(Instrument.class))).willReturn(bassGuitar);
		given(fileService.uploadFiles(eq(bassGuitarRequest.getImages()), anyString()))
			.willReturn(List.of(createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		BassGuitarDto result = sut.createNewBassGuitar(sellerId, bassGuitarRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(fileService).should().uploadFiles(eq(bassGuitarRequest.getImages()), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", bassGuitar.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	@Test
	void 어쿠스틱_클래식_기타의_정보가_주어지면_주어진_정보로_어쿠스틱_클래식_기타_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewAcousticAndClassicGuitarRequest acousticAndClassicGuitarReq = createAcousticAndClassicGuitarRequest();
		User seller = createUser(1L);
		AcousticAndClassicGuitar acousticAndClassicGuitar = createAcousticAndClassicGuitar(2L, seller);
		List<InstrumentImage> instrumentImages = List.of(createInstrumentImage(3L, acousticAndClassicGuitar));
		List<InstrumentHashtag> instrumentHashtags = List.of(createInstrumentHashtag(4L, acousticAndClassicGuitar));
		given(userQueryService.getById(sellerId)).willReturn(seller);
		given(instrumentRepository.save(any(Instrument.class))).willReturn(acousticAndClassicGuitar);
		given(fileService.uploadFiles(eq(acousticAndClassicGuitarReq.getImages()), anyString()))
			.willReturn(List.of(createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		AcousticAndClassicGuitarDto result = sut.createNewAcousticAndClassicGuitar(
			sellerId, acousticAndClassicGuitarReq
		);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(fileService).should().uploadFiles(eq(acousticAndClassicGuitarReq.getImages()), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", acousticAndClassicGuitar.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	@Test
	void 이펙터_정보가_주어지면_주어진_정보로_이펙터_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewEffectorRequest effectorRequest = createEffectorRequest();
		User seller = createUser(1L);
		Effector effector = createEffector(2L, seller);
		List<InstrumentImage> instrumentImages = List.of(createInstrumentImage(3L, effector));
		List<InstrumentHashtag> instrumentHashtags = List.of(createInstrumentHashtag(4L, effector));
		given(userQueryService.getById(sellerId)).willReturn(seller);
		given(instrumentRepository.save(any(Instrument.class))).willReturn(effector);
		given(fileService.uploadFiles(eq(effectorRequest.getImages()), anyString()))
			.willReturn(List.of(createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		EffectorDto result = sut.createNewEffector(sellerId, effectorRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(fileService).should().uploadFiles(eq(effectorRequest.getImages()), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", effector.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	@Test
	void 앰프_정보가_주어지면_주어진_정보로_앰프_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewAmplifierRequest amplifierRequest = createAmplifierRequest();
		User seller = createUser(1L);
		Amplifier amplifier = createAmplifier(2L, seller);
		List<InstrumentImage> instrumentImages = List.of(createInstrumentImage(3L, amplifier));
		List<InstrumentHashtag> instrumentHashtags = List.of(createInstrumentHashtag(4L, amplifier));
		given(userQueryService.getById(sellerId)).willReturn(seller);
		given(instrumentRepository.save(any(Instrument.class))).willReturn(amplifier);
		given(fileService.uploadFiles(eq(amplifierRequest.getImages()), anyString()))
			.willReturn(List.of(createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		AmplifierDto result = sut.createNewAmplifier(sellerId, amplifierRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(fileService).should().uploadFiles(eq(amplifierRequest.getImages()), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", amplifier.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	@Test
	void 음향_장비_정보가_주어지면_주어진_정보로_음향_장비_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewAudioEquipmentRequest audioEquipmentRequest = createAudioEquipmentRequest();
		User seller = createUser(1L);
		AudioEquipment audioEquipment = createAudioEquipment(2L, seller);
		List<InstrumentImage> instrumentImages = List.of(createInstrumentImage(3L, audioEquipment));
		List<InstrumentHashtag> instrumentHashtags = List.of(createInstrumentHashtag(4L, audioEquipment));
		given(userQueryService.getById(sellerId)).willReturn(seller);
		given(instrumentRepository.save(any(Instrument.class))).willReturn(audioEquipment);
		given(fileService.uploadFiles(eq(audioEquipmentRequest.getImages()), anyString()))
			.willReturn(List.of(createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		AudioEquipmentDto result = sut.createNewAudioEquipment(sellerId, audioEquipmentRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(fileService).should().uploadFiles(eq(audioEquipmentRequest.getImages()), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", audioEquipment.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(fileService).shouldHaveNoMoreInteractions();
		then(instrumentRepository).shouldHaveNoMoreInteractions();
		then(instrumentImageRepository).shouldHaveNoMoreInteractions();
		then(instrumentHashtagRepository).shouldHaveNoMoreInteractions();
	}

	private MockMultipartFile createMultipartFile() {
		return new MockMultipartFile(
			"image",
			"originalFilename",
			MediaType.IMAGE_PNG_VALUE,
			"content".getBytes()
		);
	}

	private User createUser(Long id) throws Exception {
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

	private Address createAddress() {
		return new Address("서울특별시", "강남구", "청담동");
	}

	private InstrumentImage createInstrumentImage(Long id, Instrument instrument) throws Exception {
		Constructor<InstrumentImage> instrumentImageConstructor = InstrumentImage.class.getDeclaredConstructor(
			Long.class, Instrument.class, String.class, String.class, String.class
		);
		instrumentImageConstructor.setAccessible(true);
		return instrumentImageConstructor.newInstance(
			id,
			instrument,
			"original-name",
			"stored-name",
			"https://instrument-image-url"
		);
	}

	private InstrumentHashtag createInstrumentHashtag(Long id, Instrument instrument) throws Exception {
		Constructor<InstrumentHashtag> instrumentHashtagConstructor = InstrumentHashtag.class.getDeclaredConstructor(
			Long.class, Instrument.class, String.class
		);
		instrumentHashtagConstructor.setAccessible(true);
		return instrumentHashtagConstructor.newInstance(id, instrument, "content");
	}

	private ElectricGuitar createElectricGuitar(Long id, User seller) throws Exception {
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

	private Amplifier createAmplifier(long id, User seller) throws Exception {
		Constructor<Amplifier> amplifierConstructor = Amplifier.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
		);
		amplifierConstructor.setAccessible(true);
		return amplifierConstructor.newInstance(
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
		Constructor<AudioEquipment> audioEquipmentConstructor = AudioEquipment.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			AudioEquipmentType.class
		);
		audioEquipmentConstructor.setAccessible(true);
		return audioEquipmentConstructor.newInstance(
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

	private FileDto createFileDto() throws Exception {
		Constructor<FileDto> fileDtoConstructor = FileDto.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		fileDtoConstructor.setAccessible(true);
		return fileDtoConstructor.newInstance("original-name", "stored-name", "https://file-url");
	}

	private AddressRequest createAddressRequest() throws Exception {
		Constructor<AddressRequest> addressRequestConstructor = AddressRequest.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		addressRequestConstructor.setAccessible(true);
		return addressRequestConstructor.newInstance("서울특별시", "강남구", "청담동");
	}

	private CreateNewElectricGuitarRequest createElectricGuitarRequest() throws Exception {
		Constructor<CreateNewElectricGuitarRequest> createNewElectricGuitarRequestConstructor =
			CreateNewElectricGuitarRequest.class.getDeclaredConstructor(
				String.class, List.class, InstrumentProgressStatus.class, AddressRequest.class,
				Short.class, Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class,
				ElectricGuitarModel.class, Short.class, GuitarColor.class, List.class
			);
		createNewElectricGuitarRequestConstructor.setAccessible(true);
		return createNewElectricGuitarRequestConstructor.newInstance(
			"Title",
			List.of(createMultipartFile()),
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			(short)2014,
			GuitarColor.RED,
			List.of("Fender", "Guitar")
		);
	}

	private CreateNewBassGuitarRequest createBassGuitarRequest() throws Exception {
		Constructor<CreateNewBassGuitarRequest> createNewBassGuitarRequestConstructor =
			CreateNewBassGuitarRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
			);
		createNewBassGuitarRequestConstructor.setAccessible(true);
		return createNewBassGuitarRequestConstructor.newInstance(
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile()),
			List.of("Fender", "Guitar"),
			BassGuitarBrand.FENDER,
			BassGuitarPickUp.JAZZ,
			BassGuitarPreAmplifier.ACTIVE,
			GuitarColor.RED
		);
	}

	private CreateNewAcousticAndClassicGuitarRequest createAcousticAndClassicGuitarRequest() throws Exception {
		Constructor<CreateNewAcousticAndClassicGuitarRequest> createNewAcousticAndClassicGuitarRequestConstructor =
			CreateNewAcousticAndClassicGuitarRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
				AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
			);
		createNewAcousticAndClassicGuitarRequestConstructor.setAccessible(true);
		return createNewAcousticAndClassicGuitarRequestConstructor.newInstance(
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile()),
			List.of("Fender", "Guitar"),
			AcousticAndClassicGuitarBrand.CORT,
			AcousticAndClassicGuitarModel.JUMBO_BODY,
			AcousticAndClassicGuitarWood.SOLID_WOOD,
			AcousticAndClassicGuitarPickUp.MICROPHONE
		);
	}

	private CreateNewEffectorRequest createEffectorRequest() throws Exception {
		Constructor<CreateNewEffectorRequest> createNewEffectorRequestConstructor =
			CreateNewEffectorRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				EffectorType.class, EffectorFeature.class
			);
		createNewEffectorRequestConstructor.setAccessible(true);
		return createNewEffectorRequestConstructor.newInstance(
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile()),
			List.of("Fender", "Guitar"),
			EffectorType.GUITAR,
			EffectorFeature.ETC
		);
	}

	private CreateNewAmplifierRequest createAmplifierRequest() throws Exception {
		Constructor<CreateNewAmplifierRequest> createNewAmplifierRequestConstructor =
			CreateNewAmplifierRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
			);
		createNewAmplifierRequestConstructor.setAccessible(true);
		return createNewAmplifierRequestConstructor.newInstance(
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile()),
			List.of("Fender", "Guitar"),
			AmplifierType.GUITAR,
			AmplifierBrand.FENDER,
			AmplifierUsage.HOME
		);
	}

	private CreateNewAudioEquipmentRequest createAudioEquipmentRequest() throws Exception {
		Constructor<CreateNewAudioEquipmentRequest> createNewAudioEquipmentRequestConstructor =
			CreateNewAudioEquipmentRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				AudioEquipmentType.class
			);
		createNewAudioEquipmentRequestConstructor.setAccessible(true);
		return createNewAudioEquipmentRequestConstructor.newInstance(
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile()),
			List.of("Fender", "Guitar"),
			AudioEquipmentType.AUDIO_EQUIPMENT
		);
	}
}
