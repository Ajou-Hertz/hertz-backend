package com.ajou.hertz.domain.instrument.entity;

import java.util.Collection;
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
public class InstrumentHashtags {

	@OneToMany(mappedBy = "instrument")
	private List<InstrumentHashtag> content = new LinkedList<>();

	public void addAll(List<InstrumentHashtag> hashtags) {
		content.addAll(hashtags);
	}

	public void deleteAllByIds(Collection<Long> ids) {
		content.removeIf(hashtag -> ids.contains(hashtag.getId()));
	}

	public List<String> toStrings() {
		return content.stream()
			.map(InstrumentHashtag::getContent)
			.toList();
	}
}
