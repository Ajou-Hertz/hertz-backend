package com.ajou.hertz.domain.instrument.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewInstrumentRequest;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.instrument.strategy.ElectricGuitarCreationStrategy;
import com.ajou.hertz.domain.instrument.strategy.InstrumentCreationStrategy;
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

	public ElectricGuitarDto createNewElectricGuitar(Long sellerId, CreateNewElectricGuitarRequest request) {
		ElectricGuitar electricGuitar = createNewInstrument(sellerId, request, new ElectricGuitarCreationStrategy());
		return ElectricGuitarDto.from(electricGuitar);
	}

	private <T extends Instrument, U extends CreateNewInstrumentRequest<T>> T createNewInstrument(
		Long sellerId,
		U request,
		InstrumentCreationStrategy<T, U> creationStrategy
	) {
		User seller = userQueryService.getById(sellerId);
		T instrument = instrumentRepository.save(creationStrategy.createInstrument(seller, request));
		registerInstrumentImages(instrument, request.getImages());
		registerInstrumentHashtags(instrument, request.getHashtags());
		return instrument;
	}

	private void registerInstrumentImages(Instrument instrument, List<MultipartFile> images) {
		List<InstrumentImage> instrumentImages = fileService
			.uploadFiles(images, INSTRUMENT_IMAGE_UPLOAD_PATH)
			.stream()
			.map(fileDto -> InstrumentImage.create(
				instrument,
				fileDto.getOriginalName(),
				fileDto.getStoredName(),
				fileDto.getUrl()
			)).toList();
		List<InstrumentImage> savedInstrumentImages = instrumentImageRepository.saveAll(instrumentImages);
		instrument.getImages().addAll(savedInstrumentImages);
	}

	private void registerInstrumentHashtags(Instrument instrument, List<String> hashtags) {
		List<InstrumentHashtag> instrumentHashtags = hashtags.stream()
			.map(hashtagContent -> InstrumentHashtag.create(instrument, hashtagContent))
			.toList();
		List<InstrumentHashtag> savedInstrumentHashtags = instrumentHashtagRepository.saveAll(instrumentHashtags);
		instrument.getHashtags().addAll(savedInstrumentHashtags);
	}
}
