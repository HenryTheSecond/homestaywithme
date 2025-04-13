package com.homestaywithme.api;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.homestay.usecase.createhomestay.dto.request.CreateHomestayRequest;
import com.homestaywithme.domain.homestay.usecase.gethomestayprices.GetHomestayPricesUseCase;
import com.homestaywithme.domain.homestay.usecase.gethomestayprices.dto.request.GetHomestayPricesRequest;
import com.homestaywithme.domain.homestay.usecase.updatehomestay.dto.request.UpdateHomestayRequest;
import com.homestaywithme.domain.homestay.usecase.createhomestay.CreateHomestayUseCase;
import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.UpdateHomestayPriceUseCase;
import com.homestaywithme.domain.homestay.usecase.updatehomestay.UpdateHomestayUseCase;
import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.dto.request.UpdateHomestayPriceRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/homestays")
public class HomestayController {
    @Autowired private CreateHomestayUseCase createHomestayUseCase;
    @Autowired private UpdateHomestayUseCase updateHomestayUseCase;
    @Autowired private UpdateHomestayPriceUseCase updateHomestayPriceUseCase;
    @Autowired private GetHomestayPricesUseCase getHomestayPricesUseCase;

    @PostMapping
    public Response createHomestay(@RequestBody CreateHomestayRequest request) {
        return createHomestayUseCase.createHomestay(request);
    }

    @PutMapping("/{id}")
    public Response updateHomestay(@PathVariable("id") Long id,
                                   @RequestBody UpdateHomestayRequest request) {
        return updateHomestayUseCase.updateHomestay(id, request);
    }

    @PutMapping("/{id}/prices")
    public Response updateHomestayPrice(@PathVariable("id") Long id,
                                        @Valid @RequestBody UpdateHomestayPriceRequest request) {
        return updateHomestayPriceUseCase.updateHomestayPrice(id, request);
    }

    @GetMapping("/{id}/prices")
    public Response getHomestayPrices(@PathVariable("id") Long id, GetHomestayPricesRequest request) {
        return getHomestayPricesUseCase.getHomestayPrices(id, request);
    }
}
