package com.ajou.hertz.domain.instrument.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class InstrumentCommandService {

	private static final String INSTRUMENT_IMAGE_UPLOAD_PATH = "instrument-image/";

	private final UserQueryService userQueryService;
	private final FileService fileService;
	private final InstrumentRepository instrumentRepository;
	private final InstrumentHashtagRepository instrumentHashtagRepository;
	private final InstrumentImageRepository instrumentImageRepository;

	public ElectricGuitarDto createNewElectricGuitar(
		Long sellerId,
		CreateNewElectricGuitarRequest createNewElectricGuitarRequest
	) {
		// 악기(Instrument) 매물 등록
		User seller = userQueryService.getById(sellerId);
		ElectricGuitar electricGuitar = instrumentRepository.save(createNewElectricGuitarRequest.toEntity(seller));

		// 악기 이미지 등록
		List<InstrumentImage> instrumentImages = fileService
			.uploadFiles(createNewElectricGuitarRequest.getImages(), INSTRUMENT_IMAGE_UPLOAD_PATH)
			.stream()
			.map(fileDto -> InstrumentImage.create(
				electricGuitar,
				fileDto.getOriginalName(),
				fileDto.getStoredName(),
				fileDto.getUrl()
			)).toList();
		List<InstrumentImage> savedInstrumentImages = instrumentImageRepository.saveAll(instrumentImages);
		electricGuitar.getImages().addAll(savedInstrumentImages);

		// 악기 해시태그 등록
		List<InstrumentHashtag> hashtags = createNewElectricGuitarRequest
			.getHashtags()
			.stream()
			.map(hashtagContent -> InstrumentHashtag.create(electricGuitar, hashtagContent))
			.toList();
		List<InstrumentHashtag> savedInstrumentHashtags = instrumentHashtagRepository.saveAll(hashtags);
		electricGuitar.getHashtags().addAll(savedInstrumentHashtags);

		return ElectricGuitarDto.from(electricGuitar);
	}
}
