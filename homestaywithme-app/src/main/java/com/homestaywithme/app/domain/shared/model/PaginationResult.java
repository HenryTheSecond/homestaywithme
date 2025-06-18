package com.homestaywithme.app.domain.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResult<T> {
    private List<T> result;
    private long total;
}
