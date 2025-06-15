package com.homestaywithme.application.kafka.producer.sethomestayprice;

import com.homestaywithme.domain.booking.repository.homestayavailability.model.SetPriceHomestayAvailability;
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