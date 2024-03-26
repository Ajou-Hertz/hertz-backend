package com.ajou.hertz.unit.domain.instrument.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Collection;
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

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentImageCommandService;
import com.ajou.hertz.domain.instrument.service.InstrumentImageQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service(Command) - Instrument Image")
@ExtendWith(MockitoExtension.class)
class InstrumentImageCommandServiceTest {

	@InjectMocks
	private InstrumentImageCommandService sut;

	@Mock
	private InstrumentImageQueryService instrumentImageQueryService;

	@Mock
	private FileService fileService;

	@Mock
	private InstrumentImageRepository instrumentImageRepository;

	@Test
	void 악기와_이미지_파일들이_주어지고_이미지_파일들을_업로드_한_후_주어진_악기에_대한_이미지_entities를_생성한다() throws Exception {
		// given
		List<MultipartFile> images = List.of(
			createMultipartFile(),
			createMultipartFile(),
			createMultipartFile(),
			createMultipartFile()
		);
		BassGuitar bassGuitar = createBassGuitar(1L, createUser(2L));
		List<InstrumentImage> expectedResult = List.of(
			createInstrumentImage(3L, bassGuitar),
			createInstrumentImage(4L, bassGuitar),
			createInstrumentImage(5L, bassGuitar),
			createInstrumentImage(6L, bassGuitar)
		);
		given(fileService.uploadFiles(ArgumentMatchers.<List<MultipartFile>>any(), anyString()))
			.willReturn(List.of(createFileDto(), createFileDto(), createFileDto(), createFileDto()));
		given(instrumentImageRepository.saveAll(ArgumentMatchers.<List<InstrumentImage>>any()))
			.willReturn(expectedResult);

		// when
		List<InstrumentImage> actualResult = sut.saveImages(bassGuitar, images);

		// then
		then(fileService).should().uploadFiles(ArgumentMatchers.<List<MultipartFile>>any(), anyString());
		then(instrumentImageRepository).should().saveAll(ArgumentMatchers.<List<InstrumentImage>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult.size()).isEqualTo(expectedResult.size());
	}

	@Test
	void 악기_id가_주어지고_악기의_이미지를_전부_삭제한다() throws Exception {
		// given
		long instrumentId = 1L;
		BassGuitar bassGuitar = createBassGuitar(instrumentId, createUser(2L));
		List<InstrumentImage> instrumentImagesToDelete = List.of(
			createInstrumentImage(2L, bassGuitar),
			createInstrumentImage(3L, bassGuitar),
			createInstrumentImage(4L, bassGuitar),
			createInstrumentImage(5L, bassGuitar)
		);
		List<String> storedImageNames = instrumentImagesToDelete.stream().map(InstrumentImage::getStoredName).toList();
		given(instrumentImageQueryService.findAllByInstrumentId(instrumentId)).willReturn(instrumentImagesToDelete);
		willDoNothing().given(fileService).deleteAll(storedImageNames);
		willDoNothing().given(instrumentImageRepository).deleteAll(instrumentImagesToDelete);

		// when
		sut.deleteAllByInstrumentId(instrumentId);

		// then
		then(instrumentImageQueryService).should().findAllByInstrumentId(instrumentId);
		then(fileService).should().deleteAll(storedImageNames);
		then(instrumentImageRepository).should().deleteAll(instrumentImagesToDelete);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 삭제할_악기_이미지들의_id_리스트가_주어지고_주어진_id_리스트에_포함된_이미지를_전부_삭제한다() throws Exception {
		// given
		List<Long> deleteImageIds = List.of(1L, 2L, 3L);
		BassGuitar bassGuitar = createBassGuitar(4L, createUser(5L));
		List<InstrumentImage> deleteImages = List.of(
			createInstrumentImage(deleteImageIds.get(0), bassGuitar),
			createInstrumentImage(deleteImageIds.get(1), bassGuitar),
			createInstrumentImage(deleteImageIds.get(2), bassGuitar)
		);
		Collection<String> deleteImageStoredNames = deleteImages.stream()
			.map(InstrumentImage::getStoredName)
			.toList();
		given(instrumentImageRepository.findAllByIdIn(deleteImageIds))
			.willReturn(deleteImages);
		willDoNothing().given(instrumentImageRepository).deleteAllInBatch(deleteImages);
		willDoNothing().given(fileService).deleteAll(deleteImageStoredNames);

		// when
		sut.deleteAllByIds(deleteImageIds);

		// then
		then(instrumentImageRepository).should().findAllByIdIn(deleteImageIds);
		then(instrumentImageRepository).should().deleteAllInBatch(deleteImages);
		then(fileService).should().deleteAll(deleteImageStoredNames);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentImageQueryService).shouldHaveNoMoreInteractions();
		then(fileService).shouldHaveNoMoreInteractions();
		then(instrumentImageRepository).shouldHaveNoMoreInteractions();
	}

	private MockMultipartFile createMultipartFile() {
		return new MockMultipartFile(
			"image",
			"originalFilename",
			MediaType.IMAGE_PNG_VALUE,
			"content".getBytes()
		);
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

	private FileDto createFileDto() throws Exception {
		return ReflectionUtils.createFileDto("original-name", "stored-name", "https://file-url");
	}
}