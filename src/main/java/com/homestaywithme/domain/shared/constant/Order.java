package com.homestaywithme.domain.shared.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Order {
    ASC("ASC"),
    DESC("DESC")
    ;
    private final String value;
}
