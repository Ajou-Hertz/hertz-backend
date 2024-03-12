package com.ajou.hertz.domain.concert_hall.entity;

import com.ajou.hertz.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ConcertHallHashtag extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "concert_hall_hashtag_id", nullable = false)
	private Long id;

	@JoinColumn(name = "concert_hall_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private ConcertHall concertHall;

	@Column(length = 10, nullable = false)
	private String content;

	public static ConcertHallHashtag create(ConcertHall concertHall, String content) {
		return new ConcertHallHashtag(null, concertHall, content);
	}
}
