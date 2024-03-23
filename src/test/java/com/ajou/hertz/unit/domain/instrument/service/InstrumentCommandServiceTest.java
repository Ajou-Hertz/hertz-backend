package com.ajou.hertz.unit.domain.instrument.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.amplifier.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.audio_equipment.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.effector.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.effector.constant.EffectorType;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.electric_guitar.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.amplifier.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.effector.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.effector.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.amplifier.entity.Amplifier;
import com.ajou.hertz.domain.instrument.audio_equipment.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.effector.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.exception.InstrumentDeletePermissionDeniedException;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;
import com.ajou.hertz.domain.instrument.service.InstrumentImageCommandService;
import com.ajou.hertz.domain.instrument.service.InstrumentQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.service.UserQueryService;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service(Command) - Instrument")
@ExtendWith(MockitoExtension.class)
class InstrumentCommandServiceTest {

	@InjectMocks
	private InstrumentCommandService sut;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private InstrumentQueryService instrumentQueryService;

	@Mock
	private InstrumentImageCommandService instrumentImageCommandService;

	@Mock
	private InstrumentRepository instrumentRepository;

	@Mock
	private InstrumentHashtagRepository instrumentHashtagRepository;

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
		given(instrumentImageCommandService.saveImages(eq(electricGuitar), ArgumentMatchers.<List<MultipartFile>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		ElectricGuitarDto result = sut.createNewElectricGuitar(sellerId, electricGuitarRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(instrumentImageCommandService)
			.should()
			.saveImages(eq(electricGuitar), ArgumentMatchers.<List<MultipartFile>>any());
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
		given(instrumentImageCommandService.saveImages(eq(bassGuitar), ArgumentMatchers.<List<MultipartFile>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		BassGuitarDto result = sut.createNewBassGuitar(sellerId, bassGuitarRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(instrumentImageCommandService).should()
			.saveImages(eq(bassGuitar), ArgumentMatchers.<List<MultipartFile>>any());
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
		given(instrumentImageCommandService.saveImages(
			eq(acousticAndClassicGuitar), ArgumentMatchers.<List<MultipartFile>>any()
		)).willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		AcousticAndClassicGuitarDto result = sut.createNewAcousticAndClassicGuitar(
			sellerId, acousticAndClassicGuitarReq
		);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(instrumentImageCommandService)
			.should()
			.saveImages(eq(acousticAndClassicGuitar), ArgumentMatchers.<List<MultipartFile>>any());
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
		given(instrumentImageCommandService.saveImages(eq(effector), ArgumentMatchers.<List<MultipartFile>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		EffectorDto result = sut.createNewEffector(sellerId, effectorRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(instrumentImageCommandService).should()
			.saveImages(eq(effector), ArgumentMatchers.<List<MultipartFile>>any());
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
		given(instrumentImageCommandService.saveImages(eq(amplifier), ArgumentMatchers.<List<MultipartFile>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		AmplifierDto result = sut.createNewAmplifier(sellerId, amplifierRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(instrumentImageCommandService).should()
			.saveImages(eq(amplifier), ArgumentMatchers.<List<MultipartFile>>any());
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
		given(instrumentImageCommandService.saveImages(eq(audioEquipment), ArgumentMatchers.<List<MultipartFile>>any()))
			.willReturn(instrumentImages);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(instrumentHashtags);

		// when
		AudioEquipmentDto result = sut.createNewAudioEquipment(sellerId, audioEquipmentRequest);

		// then
		then(userQueryService).should().getById(sellerId);
		then(instrumentRepository).should().save(any(Instrument.class));
		then(instrumentImageCommandService)
			.should()
			.saveImages(eq(audioEquipment), ArgumentMatchers.<List<MultipartFile>>any());
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(result)
			.hasFieldOrPropertyWithValue("id", audioEquipment.getId())
			.hasFieldOrPropertyWithValue("seller.id", seller.getId());
		assertThat(result.getImages()).hasSize(instrumentImages.size());
		assertThat(result.getHashtags()).hasSize(instrumentHashtags.size());
	}

	@Test
	void 악기_id가_주어지고_해당하는_악기_매물을_삭제한다() throws Exception {
		// given
		long userId = 1L;
		long instrumentId = 2L;
		BassGuitar bassGuitar = createBassGuitar(instrumentId, createUser(userId));
		given(instrumentQueryService.getInstrumentById(instrumentId)).willReturn(bassGuitar);
		willDoNothing().given(instrumentImageCommandService).deleteAllByInstrumentId(instrumentId);
		willDoNothing().given(instrumentHashtagRepository).deleteAllByInstrument(bassGuitar);
		willDoNothing().given(instrumentRepository).delete(bassGuitar);

		// when
		sut.deleteInstrumentById(userId, instrumentId);

		// then
		then(instrumentQueryService).should().getInstrumentById(instrumentId);
		then(instrumentImageCommandService).should().deleteAllByInstrumentId(instrumentId);
		then(instrumentHashtagRepository).should().deleteAllByInstrument(bassGuitar);
		then(instrumentRepository).should().delete(bassGuitar);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 악기_판매자가_아닌_유저가_악기_매물을_삭제하려고_하면_예외가_발생한다() throws Exception {
		// given
		long userId = 1L;
		long instrumentId = 2L;
		BassGuitar bassGuitar = createBassGuitar(instrumentId, createUser(3L));
		given(instrumentQueryService.getInstrumentById(instrumentId)).willReturn(bassGuitar);

		// when
		Throwable throwable = catchThrowable(() -> sut.deleteInstrumentById(userId, instrumentId));

		// then
		then(instrumentQueryService).should().getInstrumentById(instrumentId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(throwable).isInstanceOf(InstrumentDeletePermissionDeniedException.class);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(userQueryService).shouldHaveNoMoreInteractions();
		then(instrumentRepository).shouldHaveNoMoreInteractions();
		then(instrumentImageCommandService).shouldHaveNoMoreInteractions();
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

	private Address createAddress() {
		return new Address("서울특별시", "강남구", "청담동");
	}

	private InstrumentImage createInstrumentImage(Long id, Instrument instrument) throws Exception {
		return ReflectionUtils.createInstrumentImage(
			id,
			instrument,
			"original-name",
			"stored-name",
			"https://instrument-image-url"
		);
	}

	private InstrumentHashtag createInstrumentHashtag(Long id, Instrument instrument) throws Exception {
		return ReflectionUtils.createInstrumentHashtag(id, instrument, "content");
	}

	private ElectricGuitar createElectricGuitar(Long id, User seller) throws Exception {
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

	private FileDto createFileDto() throws Exception {
		return ReflectionUtils.createFileDto("original-name", "stored-name", "https://file-url");
	}

	private AddressRequest createAddressRequest() throws Exception {
		return ReflectionUtils.createAddressRequest("서울특별시", "강남구", "청담동");
	}

	private CreateNewElectricGuitarRequest createElectricGuitarRequest() throws Exception {
		return ReflectionUtils.createElectricGuitarRequest(
			"Title",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile()),
			List.of("Fender", "Guitar"),
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			(short)2014,
			GuitarColor.RED
		);
	}

	private CreateNewBassGuitarRequest createBassGuitarRequest() throws Exception {
		return ReflectionUtils.createNewBassGuitarRequest(
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
		return ReflectionUtils.createAcousticAndClassicGuitarRequest(
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
		return ReflectionUtils.createEffectorRequest(
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
		return ReflectionUtils.createAmplifierRequest(
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
		return ReflectionUtils.createAudioEquipmentRequest(
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
