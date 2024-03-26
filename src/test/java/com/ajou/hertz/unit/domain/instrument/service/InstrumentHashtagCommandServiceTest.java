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

import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.bass_guitar.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;
import com.ajou.hertz.domain.instrument.service.InstrumentHashtagCommandService;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.util.ReflectionUtils;

@DisplayName("[Unit] Service(Command) - Instrument Hashtag")
@ExtendWith(MockitoExtension.class)
class InstrumentHashtagCommandServiceTest {

	@InjectMocks
	private InstrumentHashtagCommandService sut;

	@Mock
	private InstrumentHashtagRepository instrumentHashtagRepository;

	@Test
	void 주어진_해시태그_내용들로_해시태그_entity를_생성_및_저장한다() throws Exception {
		// given
		BassGuitar bassGuitar = createBassGuitar(1L, createUser(2L));
		List<String> hashtagsContentList = List.of("test1", "test2");
		List<InstrumentHashtag> expectedResult = List.of(
			createInstrumentHashtag(3L, bassGuitar, hashtagsContentList.get(0)),
			createInstrumentHashtag(4L, bassGuitar, hashtagsContentList.get(1))
		);
		given(instrumentHashtagRepository.saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any()))
			.willReturn(expectedResult);

		// when
		List<InstrumentHashtag> actualResult = sut.saveHashtags(bassGuitar, hashtagsContentList);

		// then
		then(instrumentHashtagRepository).should().saveAll(ArgumentMatchers.<List<InstrumentHashtag>>any());
		verifyEveryMocksShouldHaveNoMoreInteractions();
		assertThat(actualResult).hasSize(expectedResult.size());
		assertThat(actualResult.get(0).getId()).isEqualTo(expectedResult.get(0).getId());
		assertThat(actualResult.get(1).getId()).isEqualTo(expectedResult.get(1).getId());
	}

	@Test
	void 악기_entity가_주어지고_주어진_악기의_모든_해시태그를_삭제한다() throws Exception {
		// given
		BassGuitar bassGuitar = createBassGuitar(1L, createUser(2L));
		willDoNothing().given(instrumentHashtagRepository).deleteAllByInstrument(bassGuitar);

		// when
		sut.deleteAllByInstrument(bassGuitar);

		// then
		then(instrumentHashtagRepository).should().deleteAllByInstrument(bassGuitar);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	@Test
	void 해시태그_id_리스트가_주어지고_주어진_id에_해당하는_모든_해시태그를_삭제한다() throws Exception {
		// given
		List<Long> deleteHashtagIds = List.of(1L, 2L);
		willDoNothing().given(instrumentHashtagRepository).deleteAllByIdInBatch(deleteHashtagIds);

		// when
		sut.deleteAllByIds(deleteHashtagIds);

		// then
		then(instrumentHashtagRepository).should().deleteAllByIdInBatch(deleteHashtagIds);
		verifyEveryMocksShouldHaveNoMoreInteractions();
	}

	private void verifyEveryMocksShouldHaveNoMoreInteractions() {
		then(instrumentHashtagRepository).shouldHaveNoMoreInteractions();
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

	private InstrumentHashtag createInstrumentHashtag(Long id, Instrument instrument, String content) throws Exception {
		return ReflectionUtils.createInstrumentHashtag(id, instrument, content);
	}
}