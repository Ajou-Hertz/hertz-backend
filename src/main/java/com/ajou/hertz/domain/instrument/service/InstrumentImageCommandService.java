package com.ajou.hertz.domain.instrument.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.file.service.FileService;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.repository.InstrumentImageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class InstrumentImageCommandService {

	private static final String INSTRUMENT_IMAGE_UPLOAD_PATH = "instrument-image/";

	private final InstrumentImageQueryService instrumentImageQueryService;
	private final FileService fileService;
	private final InstrumentImageRepository instrumentImageRepository;

	/**
	 * 전달받은 multipart files로 instrument image entity를 생성 후 저장한다.
	 *
	 * @param instrument 이미지의 대상이 되는 instrument entity
	 * @param images 저장하고자 하는 image
	 * @return 저장된 instrument images
	 */
	public List<InstrumentImage> saveImages(Instrument instrument, Iterable<MultipartFile> images) {
		List<InstrumentImage> instrumentImages = fileService
			.uploadFiles(images, INSTRUMENT_IMAGE_UPLOAD_PATH)
			.stream()
			.map(fileDto -> InstrumentImage.create(
				instrument,
				fileDto.getOriginalName(),
				fileDto.getStoredName(),
				fileDto.getUrl()
			)).toList();
		return instrumentImageRepository.saveAll(instrumentImages);
	}

	/**
	 * 특정 악기 매물의 모든 이미지를 삭제한다.
	 *
	 * @param instrumentId    이미지를 전체 삭제할 악기 매물의 id
	 */
	public void deleteAllByInstrumentId(Long instrumentId) {
		List<InstrumentImage> instrumentImages = instrumentImageQueryService.findAllByInstrumentId(instrumentId);
		fileService.deleteAll(
			instrumentImages.stream()
				.map(InstrumentImage::getStoredName)
				.toList()
		);
		instrumentImageRepository.deleteAll(instrumentImages);
	}
}
