package com.homestaywithme.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetHomestayPriceMessage {
    private List<SetPriceHomestayAvailability> listSetHomestayPriceAvailability = new ArrayList<>();
}