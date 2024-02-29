package com.ajou.hertz.domain.administrative_area.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdministrativeAreaListResponse {

	private List<AdministrativeAreaResponse> content;
}