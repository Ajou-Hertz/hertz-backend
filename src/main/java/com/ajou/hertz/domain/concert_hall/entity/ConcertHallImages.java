package com.ajou.hertz.domain.concert_hall.entity;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertHallImages {
	@OneToMany(mappedBy = "concertHall")
	private List<ConcertHallImage> content = new LinkedList<>();

}
