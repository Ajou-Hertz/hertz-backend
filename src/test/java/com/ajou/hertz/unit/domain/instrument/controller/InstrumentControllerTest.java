package com.ajou.hertz.unit.domain.instrument.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.common.auth.UserPrincipal;
import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.config.ControllerTestConfig;
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
import com.ajou.hertz.domain.instrument.controller.InstrumentController;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;
import com.ajou.hertz.domain.instrument.service.InstrumentQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;

@DisplayName("[Unit] Controller - Instrument")
@Import(ControllerTestConfig.class)
@WebMvcTest(controllers = InstrumentController.class)
class InstrumentControllerTest {

	@MockBean
	private InstrumentCommandService instrumentCommandService;

	@MockBean
	private InstrumentQueryService instrumentQueryService;

	private final MockMvc mvc;

	@Autowired
	public InstrumentControllerTest(MockMvc mvc) {
		this.mvc = mvc;
	}

	@Test
	void 전체_악기_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int pageNumber = 0;
		int pageSize = 10;
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<InstrumentDto> expectedResult = new PageImpl<>(List.of(
			createBassGuitarDto(2L, userId),
			createBassGuitarDto(3L, userId),
			createBassGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findInstruments(pageNumber, pageSize, sortOption)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(pageNumber))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService).should().findInstruments(pageNumber, pageSize, sortOption);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 일렉_기타_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int pageNumber = 0;
		int pageSize = 10;
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<ElectricGuitarDto> expectedResult = new PageImpl<>(List.of(
			createElectricGuitarDto(2L, userId),
			createElectricGuitarDto(3L, userId),
			createElectricGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findElectricGuitars(pageNumber, pageSize, sortOption)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/electric-guitars")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(pageNumber))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService).should().findElectricGuitars(pageNumber, pageSize, sortOption);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 베이스_기타_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<BassGuitarDto> expectedResult = new PageImpl<>(List.of(
			createBassGuitarDto(2L, userId),
			createBassGuitarDto(3L, userId),
			createBassGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findBassGuitars(page, pageSize, sortOption)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/bass-guitars")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService).should().findBassGuitars(page, pageSize, sortOption);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 새로_등록할_일렉기타의_정보가_주어지면_일렉기타_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewElectricGuitarRequest electricGuitarRequest = createElectricGuitarRequest();
		ElectricGuitarDto expectedResult = createElectricGuitarDto(2L, sellerId);
		given(instrumentCommandService.createNewElectricGuitar(
			eq(sellerId), any(CreateNewElectricGuitarRequest.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/instruments/electric-guitars")
					.file("images[0]", electricGuitarRequest.getImages().get(0).getBytes())
					.file("images[1]", electricGuitarRequest.getImages().get(1).getBytes())
					.file("images[2]", electricGuitarRequest.getImages().get(2).getBytes())
					.file("images[3]", electricGuitarRequest.getImages().get(3).getBytes())
					.header(API_VERSION_HEADER_NAME, 1)
					.param("title", electricGuitarRequest.getTitle())
					.param("progressStatus", electricGuitarRequest.getProgressStatus().name())
					.param("tradeAddress.sido", electricGuitarRequest.getTradeAddress().getSido())
					.param("tradeAddress.sgg", electricGuitarRequest.getTradeAddress().getSgg())
					.param("tradeAddress.emd", electricGuitarRequest.getTradeAddress().getEmd())
					.param("qualityStatus", String.valueOf(electricGuitarRequest.getQualityStatus()))
					.param("price", String.valueOf(electricGuitarRequest.getPrice()))
					.param("hasAnomaly", String.valueOf(electricGuitarRequest.getHasAnomaly()))
					.param("description", electricGuitarRequest.getDescription())
					.param("brand", electricGuitarRequest.getBrand().name())
					.param("model", electricGuitarRequest.getModel().name())
					.param("productionYear", String.valueOf(electricGuitarRequest.getProductionYear()))
					.param("color", electricGuitarRequest.getColor().name())
					.with(user(createTestUser(sellerId)))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(sellerId))
			.andExpect(jsonPath("$.images").isArray())
			.andExpect(jsonPath("$.images.size()").value(expectedResult.getImages().size()))
			.andExpect(jsonPath("$.hashtags").isArray())
			.andExpect(jsonPath("$.hashtags.size()").value(expectedResult.getHashtags().size()));
		then(instrumentCommandService).should().createNewElectricGuitar(
			eq(sellerId), any(CreateNewElectricGuitarRequest.class)
		);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 베이스_기타_정보가_주어지면_주어진_정보로_베이스_기타_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewBassGuitarRequest bassGuitarRequest = createNewBassGuitarRequest();
		BassGuitarDto expectedResult = createBassGuitarDto(2L, sellerId);
		given(instrumentCommandService.createNewBassGuitar(
			eq(sellerId), any(CreateNewBassGuitarRequest.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/instruments/bass-guitars")
					.file("images[0]", bassGuitarRequest.getImages().get(0).getBytes())
					.file("images[1]", bassGuitarRequest.getImages().get(1).getBytes())
					.file("images[2]", bassGuitarRequest.getImages().get(2).getBytes())
					.file("images[3]", bassGuitarRequest.getImages().get(3).getBytes())
					.header(API_VERSION_HEADER_NAME, 1)
					.param("title", bassGuitarRequest.getTitle())
					.param("progressStatus", bassGuitarRequest.getProgressStatus().name())
					.param("tradeAddress.sido", bassGuitarRequest.getTradeAddress().getSido())
					.param("tradeAddress.sgg", bassGuitarRequest.getTradeAddress().getSgg())
					.param("tradeAddress.emd", bassGuitarRequest.getTradeAddress().getEmd())
					.param("qualityStatus", String.valueOf(bassGuitarRequest.getQualityStatus()))
					.param("price", String.valueOf(bassGuitarRequest.getPrice()))
					.param("hasAnomaly", String.valueOf(bassGuitarRequest.getHasAnomaly()))
					.param("description", bassGuitarRequest.getDescription())
					.param("brand", bassGuitarRequest.getBrand().name())
					.param("pickUp", bassGuitarRequest.getPickUp().name())
					.param("preAmplifier", bassGuitarRequest.getPreAmplifier().name())
					.param("color", bassGuitarRequest.getColor().name())
					.with(user(createTestUser(sellerId)))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(sellerId))
			.andExpect(jsonPath("$.images").isArray())
			.andExpect(jsonPath("$.images.size()").value(expectedResult.getImages().size()))
			.andExpect(jsonPath("$.hashtags").isArray())
			.andExpect(jsonPath("$.hashtags.size()").value(expectedResult.getHashtags().size()));
		then(instrumentCommandService)
			.should()
			.createNewBassGuitar(eq(sellerId), any(CreateNewBassGuitarRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 어쿠스틱_클래식_기타_정보가_주어지면_주어진_정보로_어쿠스틱_클래식_기타_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewAcousticAndClassicGuitarRequest request = createAcousticAndClassicGuitarRequest();
		AcousticAndClassicGuitarDto expectedResult = createAcousticAndClassicGuitarDto(2L, sellerId);
		given(instrumentCommandService.createNewAcousticAndClassicGuitar(
			eq(sellerId), any(CreateNewAcousticAndClassicGuitarRequest.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/instruments/acoustic-and-classic-guitars")
					.file("images[0]", request.getImages().get(0).getBytes())
					.file("images[1]", request.getImages().get(1).getBytes())
					.file("images[2]", request.getImages().get(2).getBytes())
					.file("images[3]", request.getImages().get(3).getBytes())
					.header(API_VERSION_HEADER_NAME, 1)
					.param("title", request.getTitle())
					.param("progressStatus", request.getProgressStatus().name())
					.param("tradeAddress.sido", request.getTradeAddress().getSido())
					.param("tradeAddress.sgg", request.getTradeAddress().getSgg())
					.param("tradeAddress.emd", request.getTradeAddress().getEmd())
					.param("qualityStatus", String.valueOf(request.getQualityStatus()))
					.param("price", String.valueOf(request.getPrice()))
					.param("hasAnomaly", String.valueOf(request.getHasAnomaly()))
					.param("description", request.getDescription())
					.param("brand", request.getBrand().name())
					.param("model", request.getModel().name())
					.param("wood", request.getWood().name())
					.param("pickUp", request.getPickUp().name())
					.with(user(createTestUser(sellerId)))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(sellerId))
			.andExpect(jsonPath("$.images").isArray())
			.andExpect(jsonPath("$.images.size()").value(expectedResult.getImages().size()))
			.andExpect(jsonPath("$.hashtags").isArray())
			.andExpect(jsonPath("$.hashtags.size()").value(expectedResult.getHashtags().size()));
		then(instrumentCommandService)
			.should()
			.createNewAcousticAndClassicGuitar(eq(sellerId), any(CreateNewAcousticAndClassicGuitarRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 이펙터_정보가_주어지면_주어진_정보로_이펙터_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewEffectorRequest request = createEffectorRequest();
		EffectorDto expectedResult = createEffectorDto(2L, sellerId);
		given(instrumentCommandService.createNewEffector(
			eq(sellerId), any(CreateNewEffectorRequest.class))
		).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/instruments/effectors")
					.file("images[0]", request.getImages().get(0).getBytes())
					.file("images[1]", request.getImages().get(1).getBytes())
					.file("images[2]", request.getImages().get(2).getBytes())
					.file("images[3]", request.getImages().get(3).getBytes())
					.header(API_VERSION_HEADER_NAME, 1)
					.param("title", request.getTitle())
					.param("progressStatus", request.getProgressStatus().name())
					.param("tradeAddress.sido", request.getTradeAddress().getSido())
					.param("tradeAddress.sgg", request.getTradeAddress().getSgg())
					.param("tradeAddress.emd", request.getTradeAddress().getEmd())
					.param("qualityStatus", String.valueOf(request.getQualityStatus()))
					.param("price", String.valueOf(request.getPrice()))
					.param("hasAnomaly", String.valueOf(request.getHasAnomaly()))
					.param("description", request.getDescription())
					.param("type", request.getType().name())
					.param("feature", request.getFeature().name())
					.with(user(createTestUser(sellerId)))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(sellerId))
			.andExpect(jsonPath("$.images").isArray())
			.andExpect(jsonPath("$.images.size()").value(expectedResult.getImages().size()))
			.andExpect(jsonPath("$.hashtags").isArray())
			.andExpect(jsonPath("$.hashtags.size()").value(expectedResult.getHashtags().size()));
		then(instrumentCommandService).should().createNewEffector(eq(sellerId), any(CreateNewEffectorRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 앰프_정보가_주어지면_주어진_정보로_앰프_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewAmplifierRequest request = createAmplifierRequest();
		AmplifierDto expectedResult = createAmplifierDto(2L, sellerId);
		given(instrumentCommandService.createNewAmplifier(
			eq(sellerId), any(CreateNewAmplifierRequest.class))
		).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/instruments/amplifiers")
					.file("images[0]", request.getImages().get(0).getBytes())
					.file("images[1]", request.getImages().get(1).getBytes())
					.file("images[2]", request.getImages().get(2).getBytes())
					.file("images[3]", request.getImages().get(3).getBytes())
					.header(API_VERSION_HEADER_NAME, 1)
					.param("title", request.getTitle())
					.param("progressStatus", request.getProgressStatus().name())
					.param("tradeAddress.sido", request.getTradeAddress().getSido())
					.param("tradeAddress.sgg", request.getTradeAddress().getSgg())
					.param("tradeAddress.emd", request.getTradeAddress().getEmd())
					.param("qualityStatus", String.valueOf(request.getQualityStatus()))
					.param("price", String.valueOf(request.getPrice()))
					.param("hasAnomaly", String.valueOf(request.getHasAnomaly()))
					.param("description", request.getDescription())
					.param("type", request.getType().name())
					.param("brand", request.getBrand().name())
					.param("usage", request.getUsage().name())
					.with(user(createTestUser(sellerId)))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(sellerId))
			.andExpect(jsonPath("$.images").isArray())
			.andExpect(jsonPath("$.images.size()").value(expectedResult.getImages().size()))
			.andExpect(jsonPath("$.hashtags").isArray())
			.andExpect(jsonPath("$.hashtags.size()").value(expectedResult.getHashtags().size()));
		then(instrumentCommandService).should().createNewAmplifier(eq(sellerId), any(CreateNewAmplifierRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 음향_장비_정보가_주어지면_주어진_정보로_음향_장비_매물을_등록한다() throws Exception {
		// given
		long sellerId = 1L;
		CreateNewAudioEquipmentRequest request = createAudioEquipmentRequest();
		AudioEquipmentDto expectedResult = createAudioEquipmentDto(2L, sellerId);
		given(instrumentCommandService.createNewAudioEquipment(
			eq(sellerId), any(CreateNewAudioEquipmentRequest.class))
		).willReturn(expectedResult);

		// when & then
		mvc.perform(
				multipart("/api/instruments/audio-equipments")
					.file("images[0]", request.getImages().get(0).getBytes())
					.file("images[1]", request.getImages().get(1).getBytes())
					.file("images[2]", request.getImages().get(2).getBytes())
					.file("images[3]", request.getImages().get(3).getBytes())
					.header(API_VERSION_HEADER_NAME, 1)
					.param("title", request.getTitle())
					.param("progressStatus", request.getProgressStatus().name())
					.param("tradeAddress.sido", request.getTradeAddress().getSido())
					.param("tradeAddress.sgg", request.getTradeAddress().getSgg())
					.param("tradeAddress.emd", request.getTradeAddress().getEmd())
					.param("qualityStatus", String.valueOf(request.getQualityStatus()))
					.param("price", String.valueOf(request.getPrice()))
					.param("hasAnomaly", String.valueOf(request.getHasAnomaly()))
					.param("description", request.getDescription())
					.param("type", request.getType().name())
					.with(user(createTestUser(sellerId)))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(sellerId))
			.andExpect(jsonPath("$.images").isArray())
			.andExpect(jsonPath("$.images.size()").value(expectedResult.getImages().size()))
			.andExpect(jsonPath("$.hashtags").isArray())
			.andExpect(jsonPath("$.hashtags.size()").value(expectedResult.getHashtags().size()));
		then(instrumentCommandService)
			.should()
			.createNewAudioEquipment(eq(sellerId), any(CreateNewAudioEquipmentRequest.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentCommandService).shouldHaveNoMoreInteractions();
		then(instrumentQueryService).shouldHaveNoMoreInteractions();
	}

	private UserDetails createTestUser(Long userId) throws Exception {
		return new UserPrincipal(createUserDto(userId));
	}

	private MockMultipartFile createMultipartFile() {
		return new MockMultipartFile(
			"image",
			"originalFilename",
			MediaType.IMAGE_PNG_VALUE,
			"content".getBytes()
		);
	}

	private UserDto createUserDto(long id) throws Exception {
		Constructor<UserDto> userResponseConstructor = UserDto.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			String.class, LocalDate.class, Gender.class, String.class, String.class,
			LocalDateTime.class
		);
		userResponseConstructor.setAccessible(true);
		return userResponseConstructor.newInstance(
			id,
			Set.of(RoleType.USER),
			"test@mail.com",
			"$2a$abc123",
			"kakao-user-id",
			"https://user-default-profile-image",
			LocalDate.of(2024, 1, 1),
			Gender.ETC,
			"01012345678",
			"https://contack-link",
			LocalDateTime.of(2024, 1, 1, 0, 0)
		);
	}

	private AddressDto createAddressDto() throws Exception {
		Constructor<AddressDto> addressDtoConstructor = AddressDto.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		addressDtoConstructor.setAccessible(true);
		return addressDtoConstructor.newInstance("서울특별시", "강남구", "청담동");
	}

	private InstrumentImageDto createInstrumentImageDto(long instrumentImageId) throws Exception {
		Constructor<InstrumentImageDto> instrumentImageDtoConstructor = InstrumentImageDto.class.getDeclaredConstructor(
			Long.class, String.class, String.class
		);
		instrumentImageDtoConstructor.setAccessible(true);
		return instrumentImageDtoConstructor.newInstance(
			instrumentImageId,
			"image-name",
			"https://instrument-image-url"
		);
	}

	private ElectricGuitarDto createElectricGuitarDto(long id, long sellerId) throws Exception {
		Constructor<ElectricGuitarDto> electricGuitarDtoConstructor = ElectricGuitarDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class, ElectricGuitarModel.class,
			Short.class, GuitarColor.class, List.class, List.class
		);
		electricGuitarDtoConstructor.setAccessible(true);
		return electricGuitarDtoConstructor.newInstance(
			id,
			createUserDto(sellerId),
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressDto(),
			(short)3,
			550000,
			true,
			"description",
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			(short)2014,
			GuitarColor.RED,
			List.of(
				createInstrumentImageDto(id + 1),
				createInstrumentImageDto(id + 2),
				createInstrumentImageDto(id + 3),
				createInstrumentImageDto(id + 4)
			),
			List.of()
		);
	}

	private BassGuitarDto createBassGuitarDto(long id, long sellerId) throws Exception {
		Constructor<BassGuitarDto> bassGuitarDtoConstructor = BassGuitarDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		bassGuitarDtoConstructor.setAccessible(true);
		return bassGuitarDtoConstructor.newInstance(
			id,
			createUserDto(sellerId),
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressDto(),
			(short)3,
			550000,
			true,
			"description",
			List.of(
				createInstrumentImageDto(id + 1),
				createInstrumentImageDto(id + 2),
				createInstrumentImageDto(id + 3),
				createInstrumentImageDto(id + 4)
			),
			List.of(),
			BassGuitarBrand.FENDER,
			BassGuitarPickUp.JAZZ,
			BassGuitarPreAmplifier.ACTIVE,
			GuitarColor.RED
		);
	}

	private AcousticAndClassicGuitarDto createAcousticAndClassicGuitarDto(long id, long sellerId) throws Exception {
		Constructor<AcousticAndClassicGuitarDto> acousticAndClassicGuitarDtoConstructor =
			AcousticAndClassicGuitarDto.class.getDeclaredConstructor(
				Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
				AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
			);
		acousticAndClassicGuitarDtoConstructor.setAccessible(true);
		return acousticAndClassicGuitarDtoConstructor.newInstance(
			id,
			createUserDto(sellerId),
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressDto(),
			(short)3,
			550000,
			true,
			"description",
			List.of(
				createInstrumentImageDto(id + 1),
				createInstrumentImageDto(id + 2),
				createInstrumentImageDto(id + 3),
				createInstrumentImageDto(id + 4)
			),
			List.of(),
			AcousticAndClassicGuitarBrand.HEX,
			AcousticAndClassicGuitarModel.JUMBO_BODY,
			AcousticAndClassicGuitarWood.PLYWOOD,
			AcousticAndClassicGuitarPickUp.MICROPHONE
		);
	}

	private EffectorDto createEffectorDto(long id, long sellerId) throws Exception {
		Constructor<EffectorDto> effectorDtoConstructor = EffectorDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			EffectorType.class, EffectorFeature.class
		);
		effectorDtoConstructor.setAccessible(true);
		return effectorDtoConstructor.newInstance(
			id,
			createUserDto(sellerId),
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressDto(),
			(short)3,
			550000,
			true,
			"description",
			List.of(
				createInstrumentImageDto(id + 1),
				createInstrumentImageDto(id + 2),
				createInstrumentImageDto(id + 3),
				createInstrumentImageDto(id + 4)
			),
			List.of(),
			EffectorType.GUITAR,
			EffectorFeature.ETC
		);
	}

	private AmplifierDto createAmplifierDto(long id, long sellerId) throws Exception {
		Constructor<AmplifierDto> amplifierDtoConstructor = AmplifierDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
		);
		amplifierDtoConstructor.setAccessible(true);
		return amplifierDtoConstructor.newInstance(
			id,
			createUserDto(sellerId),
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressDto(),
			(short)3,
			550000,
			true,
			"description",
			List.of(
				createInstrumentImageDto(id + 1),
				createInstrumentImageDto(id + 2),
				createInstrumentImageDto(id + 3),
				createInstrumentImageDto(id + 4)
			),
			List.of(),
			AmplifierType.GUITAR,
			AmplifierBrand.FENDER,
			AmplifierUsage.HOME
		);
	}

	private AudioEquipmentDto createAudioEquipmentDto(long id, long sellerId) throws Exception {
		Constructor<AudioEquipmentDto> audioEquipmentDtoConstructor = AudioEquipmentDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			AudioEquipmentType.class
		);
		audioEquipmentDtoConstructor.setAccessible(true);
		return audioEquipmentDtoConstructor.newInstance(
			id,
			createUserDto(sellerId),
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressDto(),
			(short)3,
			550000,
			true,
			"description",
			List.of(
				createInstrumentImageDto(id + 1),
				createInstrumentImageDto(id + 2),
				createInstrumentImageDto(id + 3),
				createInstrumentImageDto(id + 4)
			),
			List.of(),
			AudioEquipmentType.AUDIO_EQUIPMENT
		);
	}

	private AddressRequest createAddressRequest() throws Exception {
		Constructor<AddressRequest> addressRequestConstructor = AddressRequest.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		addressRequestConstructor.setAccessible(true);
		return addressRequestConstructor.newInstance("서울특별시", "강남구", "청담동");
	}

	private CreateNewElectricGuitarRequest createElectricGuitarRequest() throws Exception {
		Constructor<CreateNewElectricGuitarRequest> createNewElectricGuitarRequestConstructor = CreateNewElectricGuitarRequest.class.getDeclaredConstructor(
			String.class, List.class, InstrumentProgressStatus.class, AddressRequest.class,
			Short.class, Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class,
			ElectricGuitarModel.class, Short.class, GuitarColor.class, List.class
		);
		createNewElectricGuitarRequestConstructor.setAccessible(true);
		return createNewElectricGuitarRequestConstructor.newInstance(
			"Test electric guitar",
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
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

	private CreateNewBassGuitarRequest createNewBassGuitarRequest() throws Exception {
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
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
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
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
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
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
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
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
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
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
			List.of("Fender", "Guitar"),
			AudioEquipmentType.AUDIO_EQUIPMENT
		);
	}
}
