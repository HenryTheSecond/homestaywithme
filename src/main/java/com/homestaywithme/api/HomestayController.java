package com.homestaywithme.api;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.homestay.usecase.createhomestay.dto.request.CreateHomestayRequest;
import com.homestaywithme.domain.homestay.usecase.gethomestayprices.GetHomestayPricesUseCase;
import com.homestaywithme.domain.homestay.usecase.gethomestayprices.dto.request.GetHomestayPricesRequest;
import com.homestaywithme.domain.homestay.usecase.searchhomestay.SearchHomestayUseCase;
import com.homestaywithme.domain.homestay.usecase.searchhomestay.dto.request.SearchHomestayRequest;
import com.homestaywithme.domain.homestay.usecase.updatehomestay.dto.request.UpdateHomestayRequest;
import com.homestaywithme.domain.homestay.usecase.createhomestay.CreateHomestayUseCase;
import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.UpdateHomestayPriceUseCase;
import com.homestaywithme.domain.homestay.usecase.updatehomestay.UpdateHomestayUseCase;
import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.dto.request.UpdateHomestayPriceRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("api/homestays")
public class HomestayController {
    private final CreateHomestayUseCase createHomestayUseCase;
    private final UpdateHomestayUseCase updateHomestayUseCase;
    private final UpdateHomestayPriceUseCase updateHomestayPriceUseCase;
    private final GetHomestayPricesUseCase getHomestayPricesUseCase;
    private final SearchHomestayUseCase searchHomestayUseCase;

    @Autowired
    public HomestayController(CreateHomestayUseCase createHomestayUseCase,
                              UpdateHomestayUseCase updateHomestayUseCase,
                              UpdateHomestayPriceUseCase updateHomestayPriceUseCase,
                              GetHomestayPricesUseCase getHomestayPricesUseCase,
                              SearchHomestayUseCase searchHomestayUseCase) {
        this.createHomestayUseCase = createHomestayUseCase;
        this.updateHomestayUseCase = updateHomestayUseCase;
        this.updateHomestayPriceUseCase = updateHomestayPriceUseCase;
        this.getHomestayPricesUseCase = getHomestayPricesUseCase;
        this.searchHomestayUseCase = searchHomestayUseCase;
    }

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

    @GetMapping
    @PreAuthorize("permitAll()")
    public Response searchHomestay(SearchHomestayRequest request) {
        return searchHomestayUseCase.searchHomestay(request);
    }
}
