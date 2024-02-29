package com.ajou.hertz.domain.instrument.entity;

import com.ajou.hertz.common.entity.FileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class InstrumentImage extends FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "instrument_image_id", nullable = false)
	private Long id;

	@JoinColumn(name = "instrument_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Instrument instrument;

	private InstrumentImage(Long id, Instrument instrument, String originalName, String storedName, String url) {
		super(originalName, storedName, url);
		this.id = id;
		this.instrument = instrument;
	}

	public static InstrumentImage create(Instrument instrument, String originalName, String storedName, String url) {
		return new InstrumentImage(
			null,
			instrument,
			originalName,
			storedName,
			url
		);
	}
}
