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
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
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
		List.of();
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
		Constructor<CreateNewElectricGuitarRequest> createNewElectricGuitarRequestConstructor = CreateNewElectricGuitarRequest.class
			.getDeclaredConstructor(String.class, List.class, InstrumentProgressStatus.class, AddressRequest.class,
				Short.class, Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class,
				ElectricGuitarModel.class, Short.class, GuitarColor.class, List.class);
		createNewElectricGuitarRequestConstructor.setAccessible(true);
		return createNewElectricGuitarRequestConstructor.newInstance(
			"Test electric guitar",
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
}
