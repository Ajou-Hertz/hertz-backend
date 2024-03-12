package com.ajou.hertz.domain.concert_hall.entity;

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
public class ConcertHallImage extends FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "concert_hall_image_id", nullable = false)
	private Long id;

	@JoinColumn(name = "concert_hall", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private ConcertHall concertHall;

	private ConcertHallImage(Long id, ConcertHall concertHall, String originalName, String storedName, String url) {
		super(originalName, storedName, url);
		this.id = id;
		this.concertHall = concertHall;
	}

	public static ConcertHallImage create(ConcertHall concertHall, String originalName, String storedName, String url) {
		return new ConcertHallImage(
			null,
			concertHall,
			originalName,
			storedName,
			url
		);
	}
}
