package com.company.pratica04.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Autorities {
	ADMIN(1L), SECRETARIO(2L), MENTOR(3L);
	private Long code;
}
