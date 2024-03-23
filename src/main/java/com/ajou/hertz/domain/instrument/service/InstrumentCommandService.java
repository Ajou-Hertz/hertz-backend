package com.ajou.hertz.domain.instrument.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.bass_guitar.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.electric_guitar.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewInstrumentRequest;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.bass_guitar.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.electric_guitar.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.instrument.exception.InstrumentDeletePermissionDeniedException;
import com.ajou.hertz.domain.instrument.mapper.InstrumentMapper;
import com.ajou.hertz.domain.instrument.repository.InstrumentHashtagRepository;
import com.ajou.hertz.domain.instrument.repository.InstrumentRepository;
import com.ajou.hertz.domain.instrument.strategy.AcousticAndClassicGuitarCreationStrategy;
import com.ajou.hertz.domain.instrument.strategy.AmplifierCreationStrategy;
import com.ajou.hertz.domain.instrument.strategy.AudioEquipmentCreationStrategy;
import com.ajou.hertz.domain.instrument.bass_guitar.strategy.BassGuitarCreationStrategy;
import com.ajou.hertz.domain.instrument.strategy.EffectorCreationStrategy;
import com.ajou.hertz.domain.instrument.electric_guitar.strategy.ElectricGuitarCreationStrategy;
import com.ajou.hertz.domain.instrument.strategy.InstrumentCreationStrategy;
import com.ajou.hertz.domain.user.entity.User;
import com.ajou.hertz.domain.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class InstrumentCommandService {

	private final UserQueryService userQueryService;
	private final InstrumentQueryService instrumentQueryService;
	private final InstrumentImageCommandService instrumentImageCommandService;
	private final InstrumentRepository instrumentRepository;
	private final InstrumentHashtagRepository instrumentHashtagRepository;

	/**
	 * 신규 일렉 기타 매물을 생성 및 저장한다.
	 *
	 * @param sellerId 악기 판매자의 id
	 * @param request  판매하고자 하는 일렉 기타의 정보
	 * @return 생성된 일렉 기타 정보가 담긴 DTO
	 */
	public ElectricGuitarDto createNewElectricGuitar(Long sellerId, CreateNewElectricGuitarRequest request) {
		ElectricGuitar electricGuitar = createNewInstrument(sellerId, request, new ElectricGuitarCreationStrategy());
		return InstrumentMapper.toElectricGuitarDto(electricGuitar);
	}

	/**
	 * 신규 베이스 기타 매물을 생성 및 저장한다.
	 *
	 * @param sellerId 악기 판매자의 id
	 * @param request  판매하고자 하는 베이스 기타의 정보
	 * @return 생성된 베이스 기타 정보가 담긴 DTO
	 */
	public BassGuitarDto createNewBassGuitar(Long sellerId, CreateNewBassGuitarRequest request) {
		BassGuitar bassGuitar = createNewInstrument(sellerId, request, new BassGuitarCreationStrategy());
		return InstrumentMapper.toBassGuitarDto(bassGuitar);
	}

	/**
	 * 신규 어쿠스틱&클래식 기타 매물을 생성 및 저장한다.
	 *
	 * @param sellerId 악기 판매자의 id
	 * @param request  판매하고자 하는 어쿠스틱&클래식 기타의 정보
	 * @return 생성된 어쿠스틱&클래식 기타 정보가 담긴 DTO
	 */
	public AcousticAndClassicGuitarDto createNewAcousticAndClassicGuitar(
		Long sellerId,
		CreateNewAcousticAndClassicGuitarRequest request
	) {
		AcousticAndClassicGuitar acousticAndClassicGuitar = createNewInstrument(
			sellerId,
			request,
			new AcousticAndClassicGuitarCreationStrategy()
		);
		return InstrumentMapper.toAcousticAndClassicGuitarDto(acousticAndClassicGuitar);
	}

	/**
	 * 신규 이펙터 매물을 생성 및 저장한다.
	 *
	 * @param sellerId 악기 판매자의 id
	 * @param request  판매하고자 하는 이펙터의 정보
	 * @return 생성된 이펙터 정보가 담긴 DTO
	 */
	public EffectorDto createNewEffector(Long sellerId, CreateNewEffectorRequest request) {
		Effector effector = createNewInstrument(sellerId, request, new EffectorCreationStrategy());
		return InstrumentMapper.toEffectorDto(effector);
	}

	/**
	 * 신규 앰프 매물을 생성 및 저장한다.
	 *
	 * @param sellerId 악기 판매자의 id
	 * @param request  판매하고자 하는 앰프의 정보
	 * @return 생성된 앰프 정보가 담긴 DTO
	 */
	public AmplifierDto createNewAmplifier(Long sellerId, CreateNewAmplifierRequest request) {
		Amplifier amplifier = createNewInstrument(sellerId, request, new AmplifierCreationStrategy());
		return InstrumentMapper.toAmplifierDto(amplifier);
	}

	/**
	 * 신규 음향 장비 매물을 생성 및 저장한다.
	 *
	 * @param sellerId 악기 판매자의 id
	 * @param request  판매하고자 하는 음향 장비의 정보
	 * @return 생성된 음향 장비 정보가 담긴 DTO
	 */
	public AudioEquipmentDto createNewAudioEquipment(Long sellerId, CreateNewAudioEquipmentRequest request) {
		AudioEquipment audioEquipment = createNewInstrument(sellerId, request, new AudioEquipmentCreationStrategy());
		return InstrumentMapper.toAmplifierDto(audioEquipment);
	}

	/**
	 * 악기 매물을 삭제한다.
	 *
	 * @param userId       악기 매물을 삭제하려는 유저의 id
	 * @param instrumentId 삭제할 악기 매물의 id
	 * @throws InstrumentDeletePermissionDeniedException 악기를 삭제하려는 유저가 판매자가 아닌 경우
	 */
	public void deleteInstrumentById(Long userId, Long instrumentId) {
		Instrument instrument = instrumentQueryService.getInstrumentById(instrumentId);
		if (!userId.equals(instrument.getSeller().getId())) {
			throw new InstrumentDeletePermissionDeniedException();
		}
		instrumentImageCommandService.deleteAllByInstrumentId(instrumentId);
		instrumentHashtagRepository.deleteAllByInstrument(instrument);
		instrumentRepository.delete(instrument);
	}

	/**
	 * 신규 악기 매물을 생성하여 저장한다.
	 *
	 * @param sellerId                   악기 판매자의 id
	 * @param createNewInstrumentRequest 판매하고자 하는 악기 정보
	 * @param creationStrategy           Instrument type별 entity 생성 전략 (strategy pattern 사용)
	 * @return 생성된 악기 entity
	 */
	private <T extends Instrument, U extends CreateNewInstrumentRequest<T>> T createNewInstrument(
		Long sellerId,
		U createNewInstrumentRequest,
		InstrumentCreationStrategy<T, U> creationStrategy
	) {
		User seller = userQueryService.getById(sellerId);
		T instrument = instrumentRepository.save(creationStrategy.createInstrument(seller, createNewInstrumentRequest));
		registerInstrumentImages(instrument, createNewInstrumentRequest.getImages());
		registerInstrumentHashtags(instrument, createNewInstrumentRequest.getHashtags());
		return instrument;
	}

	/**
	 * 전달된 이미지들을 업로드하고, <code>InstrumentImage</code> list를 만들어 저장 및 등록한다.
	 *
	 * @param instrument 이미지에 대한 악기
	 * @param images     image list
	 */
	private void registerInstrumentImages(Instrument instrument, List<MultipartFile> images) {
		List<InstrumentImage> savedInstrumentImages = instrumentImageCommandService.saveImages(instrument, images);
		instrument.getImages().addAll(savedInstrumentImages);
	}

	/**
	 * 전달된 hashtag content list로 <code>InstrumentHashtag</code> list를 만들어 저장 및 등록한다.
	 *
	 * @param instrument hashtag가 작성된 악기
	 * @param hashtags   hashtag list
	 */
	private void registerInstrumentHashtags(Instrument instrument, List<String> hashtags) {
		List<InstrumentHashtag> instrumentHashtags = hashtags.stream()
			.map(hashtagContent -> InstrumentHashtag.create(instrument, hashtagContent))
			.toList();
		List<InstrumentHashtag> savedInstrumentHashtags = instrumentHashtagRepository.saveAll(instrumentHashtags);
		instrument.getHashtags().addAll(savedInstrumentHashtags);
	}
}
