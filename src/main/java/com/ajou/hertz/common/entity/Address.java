package com.ajou.hertz.common.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Address {

	@Column(nullable = false)
	private String sido;

	@Column(nullable = false)
	private String sgg;

	@Column(nullable = false)
	private String emd;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Address address)) {
			return false;
		}
		return Objects.equals(getSido(), address.getSido())
			&& Objects.equals(getSgg(), address.getSgg())
			&& Objects.equals(getEmd(), address.getEmd());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSido(), getSgg(), getEmd());
	}
}
