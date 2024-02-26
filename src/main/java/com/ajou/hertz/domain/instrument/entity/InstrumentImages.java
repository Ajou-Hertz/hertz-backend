package com.ajou.hertz.domain.instrument.entity;

import java.util.LinkedList;
import java.util.List;

import com.ajou.hertz.domain.instrument.dto.InstrumentImageDto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class InstrumentImages {

	@OneToMany(mappedBy = "instrument")
	private List<InstrumentImage> content = new LinkedList<>();

	public void addAll(List<InstrumentImage> images) {
		content.addAll(images);
	}

	public List<InstrumentImageDto> toDtos() {
		return content.stream()
			.map(InstrumentImageDto::from)
			.toList();
	}
}
