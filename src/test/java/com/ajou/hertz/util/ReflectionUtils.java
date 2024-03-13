package com.ajou.hertz.util;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.ajou.hertz.common.auth.dto.request.KakaoLoginRequest;
import com.ajou.hertz.common.auth.dto.request.LoginRequest;
import com.ajou.hertz.common.dto.AddressDto;
import com.ajou.hertz.common.dto.request.AddressRequest;
import com.ajou.hertz.common.entity.Address;
import com.ajou.hertz.common.file.dto.FileDto;
import com.ajou.hertz.common.kakao.dto.response.KakaoTokenResponse;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaEmd;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSgg;
import com.ajou.hertz.domain.administrative_area.entity.AdministrativeAreaSido;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarModel;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.AcousticAndClassicGuitarWood;
import com.ajou.hertz.domain.instrument.constant.AmplifierBrand;
import com.ajou.hertz.domain.instrument.constant.AmplifierType;
import com.ajou.hertz.domain.instrument.constant.AmplifierUsage;
import com.ajou.hertz.domain.instrument.constant.AudioEquipmentType;
import com.ajou.hertz.domain.instrument.constant.BassGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPickUp;
import com.ajou.hertz.domain.instrument.constant.BassGuitarPreAmplifier;
import com.ajou.hertz.domain.instrument.constant.EffectorFeature;
import com.ajou.hertz.domain.instrument.constant.EffectorType;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarBrand;
import com.ajou.hertz.domain.instrument.constant.ElectricGuitarModel;
import com.ajou.hertz.domain.instrument.constant.GuitarColor;
import com.ajou.hertz.domain.instrument.constant.InstrumentProgressStatus;
import com.ajou.hertz.domain.instrument.dto.AcousticAndClassicGuitarDto;
import com.ajou.hertz.domain.instrument.dto.AmplifierDto;
import com.ajou.hertz.domain.instrument.dto.AudioEquipmentDto;
import com.ajou.hertz.domain.instrument.dto.BassGuitarDto;
import com.ajou.hertz.domain.instrument.dto.EffectorDto;
import com.ajou.hertz.domain.instrument.dto.ElectricGuitarDto;
import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;
import com.ajou.hertz.domain.instrument.dto.request.AcousticAndClassicGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AmplifierFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.AudioEquipmentFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.BassGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAcousticAndClassicGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAmplifierRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewAudioEquipmentRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewBassGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewEffectorRequest;
import com.ajou.hertz.domain.instrument.dto.request.CreateNewElectricGuitarRequest;
import com.ajou.hertz.domain.instrument.dto.request.EffectorFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.ElectricGuitarFilterConditions;
import com.ajou.hertz.domain.instrument.dto.request.InstrumentFilterConditions;
import com.ajou.hertz.domain.instrument.entity.AcousticAndClassicGuitar;
import com.ajou.hertz.domain.instrument.entity.Amplifier;
import com.ajou.hertz.domain.instrument.entity.AudioEquipment;
import com.ajou.hertz.domain.instrument.entity.BassGuitar;
import com.ajou.hertz.domain.instrument.entity.Effector;
import com.ajou.hertz.domain.instrument.entity.ElectricGuitar;
import com.ajou.hertz.domain.instrument.entity.Instrument;
import com.ajou.hertz.domain.instrument.entity.InstrumentHashtag;
import com.ajou.hertz.domain.instrument.entity.InstrumentImage;
import com.ajou.hertz.domain.user.constant.Gender;
import com.ajou.hertz.domain.user.constant.RoleType;
import com.ajou.hertz.domain.user.dto.UserDto;
import com.ajou.hertz.domain.user.dto.request.SignUpRequest;
import com.ajou.hertz.domain.user.entity.User;

public class ReflectionUtils {

	/**
	 * Entity
	 */
	public static User createUser(
		Long id,
		Set<RoleType> roleTypes,
		String email,
		String password,
		String kakaoUid,
		String profileImageUrl,
		LocalDate birth,
		Gender gender,
		String phone,
		String contactLink
	) throws Exception {
		Constructor<User> constructor = User.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			String.class, LocalDate.class, Gender.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, roleTypes, email, password, kakaoUid, profileImageUrl, birth, gender, phone, contactLink
		);
	}

	public static InstrumentImage createInstrumentImage(
		Long id,
		Instrument instrument,
		String originalName,
		String storedName,
		String url
	) throws Exception {
		Constructor<InstrumentImage> constructor = InstrumentImage.class.getDeclaredConstructor(
			Long.class, Instrument.class, String.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(id, instrument, originalName, storedName, url);
	}

	public static InstrumentHashtag createInstrumentHashtag(
		Long id,
		Instrument instrument,
		String content
	) throws Exception {
		Constructor<InstrumentHashtag> constructor = InstrumentHashtag.class.getDeclaredConstructor(
			Long.class, Instrument.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(id, instrument, content);
	}

	public static ElectricGuitar createElectricGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) throws Exception {
		Constructor<ElectricGuitar> constructor = ElectricGuitar.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class, Short.class,
			Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class, ElectricGuitarModel.class,
			Short.class, GuitarColor.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			brand, model, productionYear, color
		);
	}

	public static BassGuitar createBassGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) throws Exception {
		Constructor<BassGuitar> constructor = BassGuitar.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			brand, pickUp, preAmplifier, color
		);
	}

	public static AcousticAndClassicGuitar createAcousticAndClassicGuitar(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) throws Exception {
		Constructor<AcousticAndClassicGuitar> constructor = AcousticAndClassicGuitar.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
			AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			brand, model, wood, pickUp
		);
	}

	public static Effector createEffector(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		EffectorType type,
		EffectorFeature feature
	) throws Exception {
		Constructor<Effector> constructor = Effector.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			EffectorType.class, EffectorFeature.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			type, feature
		);
	}

	public static Amplifier createAmplifier(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) throws Exception {
		Constructor<Amplifier> constructor = Amplifier.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			type, brand, usage
		);
	}

	public static AudioEquipment createAudioEquipment(
		Long id,
		User seller,
		String title,
		InstrumentProgressStatus progressStatus,
		Address tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		AudioEquipmentType type
	) throws Exception {
		Constructor<AudioEquipment> constructor = AudioEquipment.class.getDeclaredConstructor(
			Long.class, User.class, String.class, InstrumentProgressStatus.class, Address.class,
			Short.class, Integer.class, Boolean.class, String.class,
			AudioEquipmentType.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, type
		);
	}

	public static AdministrativeAreaSido createSido(Long id, String name) throws Exception {
		Constructor<AdministrativeAreaSido> constructor = AdministrativeAreaSido.class.getDeclaredConstructor(
			Long.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(id, name);
	}

	public static AdministrativeAreaSgg createSgg(Long id, AdministrativeAreaSido sido, String name) throws
		Exception {
		Constructor<AdministrativeAreaSgg> constructor = AdministrativeAreaSgg.class.getDeclaredConstructor(
			Long.class, AdministrativeAreaSido.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(id, sido, name);
	}

	public static AdministrativeAreaEmd createEmd(Long id, AdministrativeAreaSgg sgg, String name) throws Exception {
		Constructor<AdministrativeAreaEmd> constructor = AdministrativeAreaEmd.class.getDeclaredConstructor(
			Long.class, AdministrativeAreaSgg.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(id, sgg, name);
	}

	/**
	 * DTO
	 */
	public static UserDto createUserDto(
		Long id,
		Set<RoleType> roleTypes,
		String email,
		String password,
		String kakaoUid,
		String profileImageUrl,
		LocalDate birth,
		Gender gender,
		String phone,
		String contactLink,
		LocalDateTime createdAt
	) throws Exception {
		Constructor<UserDto> constructor = UserDto.class.getDeclaredConstructor(
			Long.class, Set.class, String.class, String.class, String.class,
			String.class, LocalDate.class, Gender.class, String.class, String.class,
			LocalDateTime.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, roleTypes, email, password, kakaoUid, profileImageUrl, birth, gender, phone, contactLink, createdAt
		);
	}

	public static FileDto createFileDto(String originalName, String storedName, String url) throws Exception {
		Constructor<FileDto> constructor = FileDto.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(originalName, storedName, url);
	}

	public static AddressDto createAddressDto(String sido, String sgg, String emd) throws Exception {
		Constructor<AddressDto> constructor = AddressDto.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(sido, sgg, emd);
	}

	public static InstrumentImageDto createInstrumentImageDto(Long id, String name, String url) throws Exception {
		Constructor<InstrumentImageDto> constructor = InstrumentImageDto.class.getDeclaredConstructor(
			Long.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(id, name, url);
	}

	public static ElectricGuitarDto createElectricGuitarDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) throws Exception {
		Constructor<ElectricGuitarDto> constructor = ElectricGuitarDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class,
			Short.class, Integer.class, Boolean.class, String.class, List.class, List.class,
			ElectricGuitarBrand.class, ElectricGuitarModel.class, Short.class, GuitarColor.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress,
			qualityStatus, price, hasAnomaly, description, images, hashtags,
			brand, model, productionYear, color
		);
	}

	public static BassGuitarDto createBassGuitarDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) throws Exception {
		Constructor<BassGuitarDto> constructor = BassGuitarDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images,
			hashtags, brand, pickUp, preAmplifier, color
		);
	}

	public static AcousticAndClassicGuitarDto createAcousticAndClassicGuitarDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) throws Exception {
		Constructor<AcousticAndClassicGuitarDto> constructor = AcousticAndClassicGuitarDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
			AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images,
			hashtags, brand, model, wood, pickUp
		);
	}

	public static EffectorDto createEffectorDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		EffectorType type,
		EffectorFeature feature
	) throws Exception {
		Constructor<EffectorDto> constructor = EffectorDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class,
			Short.class, Integer.class, Boolean.class, String.class, List.class, List.class,
			EffectorType.class, EffectorFeature.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images,
			hashtags, type, feature
		);
	}

	public static AmplifierDto createAmplifierDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) throws Exception {
		Constructor<AmplifierDto> constructor = AmplifierDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images,
			hashtags, type, brand, usage
		);
	}

	public static AudioEquipmentDto createAudioEquipmentDto(
		Long id,
		UserDto seller,
		String title,
		InstrumentProgressStatus progressStatus,
		AddressDto tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<InstrumentImageDto> images,
		List<String> hashtags,
		AudioEquipmentType type
	) throws Exception {
		Constructor<AudioEquipmentDto> constructor = AudioEquipmentDto.class.getDeclaredConstructor(
			Long.class, UserDto.class, String.class, InstrumentProgressStatus.class, AddressDto.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			AudioEquipmentType.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			id, seller, title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images,
			hashtags, type
		);
	}

	/**
	 * DTO(Request)
	 */
	public static SignUpRequest createSignUpRequest(
		String email,
		String password,
		LocalDate birth,
		Gender gender,
		String phone
	) throws Exception {
		Constructor<SignUpRequest> constructor = SignUpRequest.class.getDeclaredConstructor(
			String.class, String.class, LocalDate.class, Gender.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(email, password, birth, gender, phone);
	}

	public static LoginRequest createLoginRequest(String email, String password) throws Exception {
		Constructor<LoginRequest> constructor = LoginRequest.class.getDeclaredConstructor(String.class, String.class);
		constructor.setAccessible(true);
		return constructor.newInstance(email, password);
	}

	public static KakaoLoginRequest createKakaoLoginRequest(
		String authorizationCode,
		String redirectUri
	) throws Exception {
		Constructor<KakaoLoginRequest> constructor = KakaoLoginRequest.class.getDeclaredConstructor(
			String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(authorizationCode, redirectUri);
	}

	public static AddressRequest createAddressRequest(String sido, String sgg, String emd) throws Exception {
		Constructor<AddressRequest> constructor = AddressRequest.class.getDeclaredConstructor(
			String.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(sido, sgg, emd);
	}

	public static CreateNewElectricGuitarRequest createElectricGuitarRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		Short productionYear,
		GuitarColor color
	) throws Exception {
		Constructor<CreateNewElectricGuitarRequest> constructor =
			CreateNewElectricGuitarRequest.class.getDeclaredConstructor(
				String.class, List.class, InstrumentProgressStatus.class, AddressRequest.class,
				Short.class, Integer.class, Boolean.class, String.class, ElectricGuitarBrand.class,
				ElectricGuitarModel.class, Short.class, GuitarColor.class, List.class
			);
		constructor.setAccessible(true);
		return constructor.newInstance(
			title, images, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description,
			brand, model, productionYear, color, hashtags
		);
	}

	public static CreateNewBassGuitarRequest createNewBassGuitarRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) throws Exception {
		Constructor<CreateNewBassGuitarRequest> constructor = CreateNewBassGuitarRequest.class.getDeclaredConstructor(
			String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags,
			brand, pickUp, preAmplifier, color
		);
	}

	public static CreateNewAcousticAndClassicGuitarRequest createAcousticAndClassicGuitarRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) throws Exception {
		Constructor<CreateNewAcousticAndClassicGuitarRequest> constructor =
			CreateNewAcousticAndClassicGuitarRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
				AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
			);
		constructor.setAccessible(true);
		return constructor.newInstance(
			title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags,
			brand, model, wood, pickUp
		);
	}

	public static CreateNewEffectorRequest createEffectorRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		EffectorType type,
		EffectorFeature feature
	) throws Exception {
		Constructor<CreateNewEffectorRequest> constructor = CreateNewEffectorRequest.class.getDeclaredConstructor(
			String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			EffectorType.class, EffectorFeature.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags,
			type, feature
		);
	}

	public static CreateNewAmplifierRequest createAmplifierRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) throws Exception {
		Constructor<CreateNewAmplifierRequest> constructor = CreateNewAmplifierRequest.class.getDeclaredConstructor(
			String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
			Integer.class, Boolean.class, String.class, List.class, List.class,
			AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags,
			type, brand, usage
		);
	}

	public static CreateNewAudioEquipmentRequest createAudioEquipmentRequest(
		String title,
		InstrumentProgressStatus progressStatus,
		AddressRequest tradeAddress,
		Short qualityStatus,
		Integer price,
		Boolean hasAnomaly,
		String description,
		List<MultipartFile> images,
		List<String> hashtags,
		AudioEquipmentType type
	) throws Exception {
		Constructor<CreateNewAudioEquipmentRequest> constructor =
			CreateNewAudioEquipmentRequest.class.getDeclaredConstructor(
				String.class, InstrumentProgressStatus.class, AddressRequest.class, Short.class,
				Integer.class, Boolean.class, String.class, List.class, List.class,
				AudioEquipmentType.class
			);
		constructor.setAccessible(true);
		return constructor.newInstance(
			title, progressStatus, tradeAddress, qualityStatus, price, hasAnomaly, description, images, hashtags, type
		);
	}

	public static InstrumentFilterConditions createInstrumentFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg
	) throws Exception {
		Constructor<InstrumentFilterConditions> constructor = InstrumentFilterConditions.class.getDeclaredConstructor(
			InstrumentProgressStatus.class, String.class, String.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg);
	}

	public static ElectricGuitarFilterConditions createElectricGuitarFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg,
		ElectricGuitarBrand brand,
		ElectricGuitarModel model,
		GuitarColor color
	) throws Exception {
		Constructor<ElectricGuitarFilterConditions> constructor =
			ElectricGuitarFilterConditions.class.getDeclaredConstructor(
				InstrumentProgressStatus.class, String.class, String.class,
				ElectricGuitarBrand.class, ElectricGuitarModel.class, GuitarColor.class
			);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg, brand, model, color);
	}

	public static BassGuitarFilterConditions createBassGuitarFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg,
		BassGuitarBrand brand,
		BassGuitarPickUp pickUp,
		BassGuitarPreAmplifier preAmplifier,
		GuitarColor color
	) throws Exception {
		Constructor<BassGuitarFilterConditions> constructor = BassGuitarFilterConditions.class.getDeclaredConstructor(
			InstrumentProgressStatus.class, String.class, String.class,
			BassGuitarBrand.class, BassGuitarPickUp.class, BassGuitarPreAmplifier.class, GuitarColor.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg, brand, pickUp, preAmplifier, color);
	}

	public static AcousticAndClassicGuitarFilterConditions createAcousticAndClassicGuitarFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg,
		AcousticAndClassicGuitarBrand brand,
		AcousticAndClassicGuitarModel model,
		AcousticAndClassicGuitarWood wood,
		AcousticAndClassicGuitarPickUp pickUp
	) throws Exception {
		Constructor<AcousticAndClassicGuitarFilterConditions> constructor =
			AcousticAndClassicGuitarFilterConditions.class.getDeclaredConstructor(
				InstrumentProgressStatus.class, String.class, String.class,
				AcousticAndClassicGuitarBrand.class, AcousticAndClassicGuitarModel.class,
				AcousticAndClassicGuitarWood.class, AcousticAndClassicGuitarPickUp.class
			);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg, brand, model, wood, pickUp);
	}

	public static EffectorFilterConditions createEffectorFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg,
		EffectorType type,
		EffectorFeature feature
	) throws Exception {
		Constructor<EffectorFilterConditions> constructor = EffectorFilterConditions.class.getDeclaredConstructor(
			InstrumentProgressStatus.class, String.class, String.class,
			EffectorType.class, EffectorFeature.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg, type, feature);
	}

	public static AmplifierFilterConditions createAmplifierFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg,
		AmplifierType type,
		AmplifierBrand brand,
		AmplifierUsage usage
	) throws Exception {
		Constructor<AmplifierFilterConditions> constructor = AmplifierFilterConditions.class.getDeclaredConstructor(
			InstrumentProgressStatus.class, String.class, String.class,
			AmplifierType.class, AmplifierBrand.class, AmplifierUsage.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg, type, brand, usage);
	}

	public static AudioEquipmentFilterConditions createAudioEquipmentFilterConditions(
		InstrumentProgressStatus progressStatus,
		String sido,
		String sgg,
		AudioEquipmentType type
	) throws Exception {
		Constructor<AudioEquipmentFilterConditions> constructor =
			AudioEquipmentFilterConditions.class.getDeclaredConstructor(
				InstrumentProgressStatus.class, String.class, String.class, AudioEquipmentType.class
			);
		constructor.setAccessible(true);
		return constructor.newInstance(progressStatus, sido, sgg, type);
	}

	/**
	 * DTO(Response)
	 */
	public static KakaoTokenResponse createKakaoTokenResponse(
		String tokenType,
		String accessToken,
		Integer expiresIn,
		String refreshToken,
		Integer refreshTokenExpiresIn
	) throws Exception {
		Constructor<KakaoTokenResponse> constructor = KakaoTokenResponse.class.getDeclaredConstructor(
			String.class, String.class, Integer.class, String.class, Integer.class
		);
		constructor.setAccessible(true);
		return constructor.newInstance(
			tokenType, accessToken, expiresIn, refreshToken, refreshTokenExpiresIn
		);
	}
}
