package com.ajou.hertz.unit.domain.administrative_area.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.config.ControllerTestConfig;
import com.ajou.hertz.domain.administrative_area.controller.AdministrativeAreaControllerV1;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaEmd;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSgg;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSido;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeEmdRepository;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeSggRepository;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeSidoRepository;

@DisplayName("[Unit] Controller - AdministrativeArea(V1)")
@Import(ControllerTestConfig.class)
@WebMvcTest(controllers = AdministrativeAreaControllerV1.class)
public class AdministrativeAreaControllerV1Test {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AdministrativeSidoRepository administrativeSidoRepository;

	@MockBean
	private AdministrativeSggRepository administrativeSggRepository;

	@MockBean
	private AdministrativeEmdRepository administrativeEmdRepository;

	@Test
	void 행정구역_시도를_조회한다() throws Exception {
		// given
		List<AdministrativeAreaSido> expectedResult = List.of(createEntity(AdministrativeAreaSido.class, 1L, "시도"));
		given(administrativeSidoRepository.findAll()).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/v1/administrative-areas/sido")
					.header(API_MINOR_VERSION_HEADER_NAME, 1)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)))
			.andExpect(jsonPath("$.content[0].id").value(expectedResult.get(0).getId()))
			.andExpect(jsonPath("$.content[0].name").value(expectedResult.get(0).getName()));
		then(administrativeSidoRepository).should().findAll();
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 시도id값을_통해_시군구를_조회한다() throws Exception {
		// given
		AdministrativeAreaSido sido = createEntity(AdministrativeAreaSido.class, 1L, "시도");
		AdministrativeAreaSgg sgg = createEntity(AdministrativeAreaSgg.class, 1L, sido, "시군구");
		List<AdministrativeAreaSgg> expectedResult = List.of(sgg);
		given(administrativeSggRepository.findBySido_Id(anyLong())).willReturn(expectedResult);

		// when & then
		mvc.perform(get("/v1/administrative-areas/sgg")
				.header(API_MINOR_VERSION_HEADER_NAME, 1)
				.param("sidoId", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)))
			.andExpect(jsonPath("$.content[0].id", is(1)))
			.andExpect(jsonPath("$.content[0].name", is("시군구")));
		then(administrativeSggRepository).should().findBySido_Id(1L);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 시도id와_시군구id값을_통해_읍면동을_조회한다() throws Exception {
		// given
		AdministrativeAreaSido sido = createEntity(AdministrativeAreaSido.class, 1L, "시도");
		AdministrativeAreaSgg sgg = createEntity(AdministrativeAreaSgg.class, 1L, sido, "시군구");
		AdministrativeAreaEmd emd = createEntity(AdministrativeAreaEmd.class, 1L, sgg, "읍면동");
		List<AdministrativeAreaEmd> expectedResult = List.of(emd);
		given(administrativeEmdRepository.findBySgg_Id(anyLong())).willReturn(expectedResult);

		// when & then
		mvc.perform(get("/v1/administrative-areas/emd")
				.header(API_MINOR_VERSION_HEADER_NAME, 1)
				.param("sggId", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)))
			.andExpect(jsonPath("$.content[0].id", is(1)))
			.andExpect(jsonPath("$.content[0].name", is("읍면동")));
		then(administrativeEmdRepository).should().findBySgg_Id(1L);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private <T> T createEntity(Class<T> clazz, Object... args) throws Exception {
		Class<?>[] argClasses = Arrays.stream(args)
			.map(Object::getClass)
			.toArray(Class[]::new);
		Constructor<T> constructor = clazz.getDeclaredConstructor(argClasses);
		constructor.setAccessible(true);
		return constructor.newInstance(args);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(administrativeSidoRepository).shouldHaveNoMoreInteractions();
		then(administrativeSggRepository).shouldHaveNoMoreInteractions();
		then(administrativeEmdRepository).shouldHaveNoMoreInteractions();
	}
}
