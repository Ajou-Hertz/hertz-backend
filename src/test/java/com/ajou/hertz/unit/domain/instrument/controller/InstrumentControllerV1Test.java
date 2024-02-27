package com.ajou.hertz.unit.domain.instrument.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.common.auth.UserPrincipal;
import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.config.ControllerTestConfig;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.controller.InstrumentControllerV1;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.service.InstrumentCommandService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;

@DisplayName("[Unit] Controller - Instrument(V1)")
@Import(ControllerTestConfig.class)
@WebMvcTest(controllers = InstrumentControllerV1.class)
class InstrumentControllerV1Test {

	@MockBean
	private InstrumentCommandService instrumentCommandService;

	private final MockMvc mvc;

	@Autowired
	public InstrumentControllerV1Test(MockMvc mvc) {
		this.mvc = mvc;
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
				multipart("/v1/instruments/electric-guitars")
					.file("images[0]", electricGuitarRequest.getImages().get(0).getBytes())
					.file("images[1]", electricGuitarRequest.getImages().get(1).getBytes())
					.file("images[2]", electricGuitarRequest.getImages().get(2).getBytes())
					.file("images[3]", electricGuitarRequest.getImages().get(3).getBytes())
					.header(API_MINOR_VERSION_HEADER_NAME, 1)
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

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentCommandService).shouldHaveNoMoreInteractions();
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
			List.of(),
			List.of()
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
		Constructor<CreateNewElectricGuitarRequest> createNewElectricGuitarRequestConstructor = CreateNewElectricGuitarRequest.class
			.getDeclaredConstructor(String.class, List.class, InstrumentProgressStatus.class, AddressRequest.class,
				Short.class, Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class,
				ElectricGuitarModel.class, Short.class, GuitarColor.class, List.class);
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
}