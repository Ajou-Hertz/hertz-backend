package com.ajou.hertz.unit.domain.instrument.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.ajou.hertz.domain.instrument.constant.InstrumentCategory;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.constant.InstrumentSortOption;
import com.ajou.hertz.domain.instrument.controller.InstrumentController;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.amplifier.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.effector.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.acoustic_and_classic_guitar.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.amplifier.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.audio_equipment.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.effector.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.effector.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;
import com.ajou.hertz.domain.instrument.service.InstrumentQueryService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.util.ReflectionUtils;

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
	void id가_주어지고_id에_해당하는_악기를_조회한다() throws Exception {
		// given
		long instrumentId = 1L;
		InstrumentDto expectedResult = createAmplifierDto(instrumentId, 2L);
		given(instrumentQueryService.getInstrumentDtoById(instrumentId)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/{instrumentId}", instrumentId)
					.header(API_VERSION_HEADER_NAME, 1)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(expectedResult.getId()))
			.andExpect(jsonPath("$.sellerId").value(expectedResult.getSeller().getId()))
			.andExpect(jsonPath("$.category").value(expectedResult.getCategory().name()));
		then(instrumentQueryService).should().getInstrumentDtoById(instrumentId);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 종류_상관_없이_전체_악기_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<InstrumentDto> expectedResult = new PageImpl<>(List.of(
			createElectricGuitarDto(2L, userId),
			createBassGuitarDto(3L, userId),
			createAcousticAndClassicGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findInstrumentDtos(page, pageSize, sortOption)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())))
			.andExpect(jsonPath("$.content[0].category").value(InstrumentCategory.ELECTRIC_GUITAR.name()))
			.andExpect(jsonPath("$.content[1].category").value(InstrumentCategory.BASS_GUITAR.name()))
			.andExpect(jsonPath("$.content[2].category").value(InstrumentCategory.ACOUSTIC_AND_CLASSIC_GUITAR.name()));
		then(instrumentQueryService).should().findInstrumentDtos(page, pageSize, sortOption);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 필터링_조건이_주어지고_주어진_조건에_해당하는_일렉_기타_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<ElectricGuitarDto> expectedResult = new PageImpl<>(List.of(
			createElectricGuitarDto(2L, userId),
			createElectricGuitarDto(3L, userId),
			createElectricGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findElectricGuitarDtos(
			eq(page), eq(pageSize), eq(sortOption), any(ElectricGuitarFilterConditions.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/electric-guitars")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
					.param("brand", ElectricGuitarBrand.FENDER_USA.name())
					.param("model", ElectricGuitarModel.TELECASTER.name())
					.param("color", GuitarColor.RED.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService)
			.should()
			.findElectricGuitarDtos(eq(page), eq(pageSize), eq(sortOption), any(ElectricGuitarFilterConditions.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 필터링_조건이_주어지고_주어진_조건에_해당하는_베이스_기타_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentFilterConditions filterConditions = createInstrumentFilterConditions();
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<BassGuitarDto> expectedResult = new PageImpl<>(List.of(
			createBassGuitarDto(2L, userId),
			createBassGuitarDto(3L, userId),
			createBassGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findBassGuitarDtos(
			eq(page), eq(pageSize), eq(sortOption), any(BassGuitarFilterConditions.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/bass-guitars")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
					.param("brand", BassGuitarBrand.FENDER.name())
					.param("pickUp", BassGuitarPickUp.ETC.name())
					.param("preAmplifier", BassGuitarPreAmplifier.ACTIVE.name())
					.param("color", GuitarColor.BLUE.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService)
			.should()
			.findBassGuitarDtos(eq(page), eq(pageSize), eq(sortOption), any(BassGuitarFilterConditions.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 필터링_조건이_주어지고_주어진_조건에_해당하는_어쿠스틱_클래식_기타_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentFilterConditions filterConditions = createInstrumentFilterConditions();
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<AcousticAndClassicGuitarDto> expectedResult = new PageImpl<>(List.of(
			createAcousticAndClassicGuitarDto(2L, userId),
			createAcousticAndClassicGuitarDto(3L, userId),
			createAcousticAndClassicGuitarDto(4L, userId)
		));
		given(instrumentQueryService.findAcousticAndClassicGuitarDtos(
			eq(page), eq(pageSize), eq(sortOption), any(AcousticAndClassicGuitarFilterConditions.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/acoustic-and-classic-guitars")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
					.param("brand", AcousticAndClassicGuitarBrand.CORT.name())
					.param("model", AcousticAndClassicGuitarModel.JUMBO_BODY.name())
					.param("wood", AcousticAndClassicGuitarWood.SOLID_WOOD.name())
					.param("pickUp", AcousticAndClassicGuitarPickUp.MICROPHONE.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService)
			.should()
			.findAcousticAndClassicGuitarDtos(
				eq(page), eq(pageSize), eq(sortOption), any(AcousticAndClassicGuitarFilterConditions.class)
			);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 필터링_조건이_주어지고_주어진_조건에_해당하는_이펙터_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentFilterConditions filterConditions = createInstrumentFilterConditions();
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<EffectorDto> expectedResult = new PageImpl<>(List.of(
			createEffectorDto(2L, userId),
			createEffectorDto(3L, userId),
			createEffectorDto(4L, userId)
		));
		given(instrumentQueryService.findEffectorDtos(
			eq(page), eq(pageSize), eq(sortOption), any(EffectorFilterConditions.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/effectors")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
					.param("type", EffectorType.GUITAR.name())
					.param("feature", EffectorFeature.BASS_COMPRESSOR.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService)
			.should()
			.findEffectorDtos(eq(page), eq(pageSize), eq(sortOption), any(EffectorFilterConditions.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 필터링_조건이_주어지고_주어진_조건에_해당하는_앰프_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentFilterConditions filterConditions = createInstrumentFilterConditions();
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<AmplifierDto> expectedResult = new PageImpl<>(List.of(
			createAmplifierDto(2L, userId),
			createAmplifierDto(3L, userId),
			createAmplifierDto(4L, userId)
		));
		given(instrumentQueryService.findAmplifierDtos(
			eq(page), eq(pageSize), eq(sortOption), any(AmplifierFilterConditions.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/amplifiers")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
					.param("type", AmplifierType.GUITAR.name())
					.param("brand", AmplifierBrand.FENDER.name())
					.param("usage", AmplifierUsage.HOME.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService)
			.should()
			.findAmplifierDtos(eq(page), eq(pageSize), eq(sortOption), any(AmplifierFilterConditions.class));
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 필터링_조건이_주어지고_주어진_조건에_해당하는_음향_장비_매물_목록을_조회한다() throws Exception {
		// given
		long userId = 1L;
		int page = 0;
		int pageSize = 10;
		InstrumentFilterConditions filterConditions = createInstrumentFilterConditions();
		InstrumentSortOption sortOption = InstrumentSortOption.CREATED_BY_DESC;
		Page<AudioEquipmentDto> expectedResult = new PageImpl<>(List.of(
			createAudioEquipmentDto(2L, userId),
			createAudioEquipmentDto(3L, userId),
			createAudioEquipmentDto(4L, userId)
		));
		given(instrumentQueryService.findAudioEquipmentDtos(
			eq(page), eq(pageSize), eq(sortOption), any(AudioEquipmentFilterConditions.class)
		)).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/instruments/audio-equipments")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(pageSize))
					.param("sort", sortOption.name())
					.param("progress", InstrumentProgressStatus.SELLING.name())
					.param("sido", "서울특별시")
					.param("sgg", "종로구")
					.param("type", AudioEquipmentType.AUDIO_EQUIPMENT.name())
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfElements").value(expectedResult.getNumberOfElements()))
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(expectedResult.getNumberOfElements())));
		then(instrumentQueryService)
			.should()
			.findAudioEquipmentDtos(eq(page), eq(pageSize), eq(sortOption), any(AudioEquipmentFilterConditions.class));
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
			eq(sellerId), any(CreateNewEffectorRequest.class)
		)).willReturn(expectedResult);

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
			eq(sellerId), any(CreateNewAmplifierRequest.class)
		)).willReturn(expectedResult);

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
			eq(sellerId), any(CreateNewAudioEquipmentRequest.class)
		)).willReturn(expectedResult);

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

	@Test
	void 악기_id가_주어지고_해당하는_악기_매물을_삭제한다() throws Exception {
		// given
		long userId = 1L;
		long instrumentId = 2L;
		willDoNothing().given(instrumentCommandService).deleteInstrumentById(userId, instrumentId);

		// when & then
		mvc.perform(
				delete("/api/instruments/{instrumentId}", instrumentId)
					.header(API_VERSION_HEADER_NAME, 1)
					.with(user(createTestUser(userId)))
			)
			.andExpect(status().isNoContent());
		then(instrumentCommandService).should().deleteInstrumentById(userId, instrumentId);
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
		return ReflectionUtils.createUserDto(
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
		return ReflectionUtils.createAddressDto("서울특별시", "강남구", "청담동");
	}

	private InstrumentImageDto createInstrumentImageDto(long instrumentImageId) throws Exception {
		return ReflectionUtils.createInstrumentImageDto(
			instrumentImageId,
			"image-name",
			"https://instrument-image-url"
		);
	}

	private ElectricGuitarDto createElectricGuitarDto(long id, long sellerId) throws Exception {
		return ReflectionUtils.createElectricGuitarDto(
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
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			(short)2014,
			GuitarColor.RED
		);
	}

	private BassGuitarDto createBassGuitarDto(long id, long sellerId) throws Exception {
		return ReflectionUtils.createBassGuitarDto(
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
		return ReflectionUtils.createAcousticAndClassicGuitarDto(
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
		return ReflectionUtils.createEffectorDto(
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
		return ReflectionUtils.createAmplifierDto(
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
		return ReflectionUtils.createAudioEquipmentDto(
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
		return ReflectionUtils.createAddressRequest("서울특별시", "강남구", "청담동");
	}

	private CreateNewElectricGuitarRequest createElectricGuitarRequest() throws Exception {
		return ReflectionUtils.createElectricGuitarRequest(
			"Test electric guitar",
			InstrumentProgressStatus.SELLING,
			createAddressRequest(),
			(short)3,
			550000,
			true,
			"description",
			List.of(createMultipartFile(), createMultipartFile(), createMultipartFile(), createMultipartFile()),
			List.of("Fender", "Guitar"),
			ElectricGuitarBrand.FENDER_USA,
			ElectricGuitarModel.TELECASTER,
			(short)2014,
			GuitarColor.RED
		);
	}

	private CreateNewBassGuitarRequest createNewBassGuitarRequest() throws Exception {
		return ReflectionUtils.createNewBassGuitarRequest(
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
		return ReflectionUtils.createAcousticAndClassicGuitarRequest(
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
		return ReflectionUtils.createEffectorRequest(
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
		return ReflectionUtils.createAmplifierRequest(
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
		return ReflectionUtils.createAudioEquipmentRequest(
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

	private InstrumentFilterConditions createInstrumentFilterConditions() throws Exception {
		return ReflectionUtils.createInstrumentFilterConditions(
			InstrumentProgressStatus.SELLING,
			"서울특별시",
			"마포구"
		);
	}
}
