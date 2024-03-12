package com.ajou.hertz.unit.domain.administrative_area.controller;

import static com.ajou.hertz.common.constant.GlobalConstants.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.ajou.hertz.config.ControllerTestConfig;
import com.ajou.hertz.domain.administrative_area.controller.AdministrativeAreaController;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaEmd;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSgg;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSido;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeEmdRepository;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeSggRepository;
import com.ajou.hertz.domain.administrative_area.repository.AdministrativeSidoRepository;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Controller - AdministrativeArea")
@Import(ControllerTestConfig.class)
@WebMvcTest(controllers = AdministrativeAreaController.class)
public class AdministrativeAreaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AdministrativeSidoRepository administrativeSidoRepository;

	@MockBean
	private AdministrativeSggRepository administrativeSggRepository;

	@MockBean
	private AdministrativeEmdRepository administrativeEmdRepository;

	@Test
	void 전체_시도_목록을_조회한다() throws Exception {
		// given
		List<AdministrativeAreaSido> expectedResult = List.of(createSido(1L, "서울특별시"));
		given(administrativeSidoRepository.findAll()).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/administrative-areas/sido")
					.header(API_VERSION_HEADER_NAME, 1)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(1)))
			.andExpect(jsonPath("$.content[0].id").value(expectedResult.get(0).getId()))
			.andExpect(jsonPath("$.content[0].name").value(expectedResult.get(0).getName()));
		then(administrativeSidoRepository).should().findAll();
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 시도_id가_주어지면_해당하는_시도에_대한_시군구_목록을_조회한다() throws Exception {
		// given
		AdministrativeAreaSido sido = createSido(1L, "서울특별시");
		List<AdministrativeAreaSgg> expectedResult = List.of(
			createSgg(2L, sido, "강남구"),
			createSgg(3L, sido, "서초구")
		);
		given(administrativeSggRepository.findAllBySido_Id(sido.getId())).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/administrative-areas/sgg")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("sidoId", String.valueOf(sido.getId()))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(2)))
			.andExpect(jsonPath("$.content[0].id").value(expectedResult.get(0).getId()))
			.andExpect(jsonPath("$.content[0].name").value(expectedResult.get(0).getName()))
			.andExpect(jsonPath("$.content[1].id").value(expectedResult.get(1).getId()))
			.andExpect(jsonPath("$.content[1].name").value(expectedResult.get(1).getName()));
		then(administrativeSggRepository).should().findAllBySido_Id(1L);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 시군구_id가_주어지면_해당하는_시군구에_대한_읍면동_목록을_조회한다() throws Exception {
		// given
		AdministrativeAreaSido sido = createSido(1L, "서울특별시");
		AdministrativeAreaSgg sgg = createSgg(2L, sido, "강남구");
		List<AdministrativeAreaEmd> expectedResult = List.of(
			createEmd(3L, sgg, "청담동"),
			createEmd(4L, sgg, "신사동")
		);
		given(administrativeEmdRepository.findAllBySgg_Id(sgg.getId())).willReturn(expectedResult);

		// when & then
		mvc.perform(
				get("/api/administrative-areas/emd")
					.header(API_VERSION_HEADER_NAME, 1)
					.param("sggId", String.valueOf(sgg.getId()))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").isArray())
			.andExpect(jsonPath("$.content", hasSize(2)))
			.andExpect(jsonPath("$.content[0].id").value(expectedResult.get(0).getId()))
			.andExpect(jsonPath("$.content[0].name").value(expectedResult.get(0).getName()))
			.andExpect(jsonPath("$.content[1].id").value(expectedResult.get(1).getId()))
			.andExpect(jsonPath("$.content[1].name").value(expectedResult.get(1).getName()));
		then(administrativeEmdRepository).should().findAllBySgg_Id(sgg.getId());
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private AdministrativeAreaSido createSido(long id, String name) throws Exception {
		return ReflectionUtils.createSido(id, name);
	}

	private AdministrativeAreaSgg createSgg(long id, AdministrativeAreaSido sido, String name) throws Exception {
		return ReflectionUtils.createSgg(id, sido, name);
	}

	private AdministrativeAreaEmd createEmd(long id, AdministrativeAreaSgg sgg, String name) throws Exception {
		return ReflectionUtils.createEmd(id, sgg, name);
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(administrativeSidoRepository).shouldHaveNoMoreInteractions();
		then(administrativeSggRepository).shouldHaveNoMoreInteractions();
		then(administrativeEmdRepository).shouldHaveNoMoreInteractions();
	}
}
