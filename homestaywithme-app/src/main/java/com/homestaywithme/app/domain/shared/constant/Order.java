package com.homestaywithme.app.domain.shared.constant;

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
