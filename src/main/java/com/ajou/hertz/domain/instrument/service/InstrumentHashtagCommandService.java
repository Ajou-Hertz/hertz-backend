package com.ajou.hertz.domain.instrument.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class InstrumentHashtagCommandService {

	private final InstrumentHashtagRepository instrumentHashtagRepository;

	/**
	 * 전달된 hashtag content list로 <code>InstrumentHashtag</code> list를 만들어 저장 및 등록한다.
	 *
	 * @param instrument hashtag가 작성된 악기
	 * @param hashtags   hashtag list
	 * @return 생성된 hashtag list
	 */
	public List<InstrumentHashtag> saveHashtags(Instrument instrument, List<String> hashtags) {
		List<InstrumentHashtag> instrumentHashtags = hashtags.stream()
			.map(hashtagContent -> InstrumentHashtag.create(instrument, hashtagContent))
			.toList();
		return instrumentHashtagRepository.saveAll(instrumentHashtags);
	}

	/**
	 * 특정 악기에 대해 모든 해시태그를 삭제한다.
	 *
	 * @param instrument 해시태그를 삭제할 악기
	 */
	public void deleteAllByInstrument(Instrument instrument) {
		instrumentHashtagRepository.deleteAllByInstrument(instrument);
	}

	/**
	 * 전달받은 id 리스트에 해당하는 해시태그를 전부 삭제한다.
	 *
	 * @param ids 삭제할 해시태그 id 리스트
	 */
	public void deleteAllByIds(Collection<Long> ids) {
		instrumentHashtagRepository.deleteAllByIdInBatch(ids);
	}
}
