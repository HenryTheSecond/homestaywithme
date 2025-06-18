package com.homestaywithme.app.api;

import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.application.service.ResponseService;
import com.homestaywithme.app.domain.homestay.usecase.createhomestay.dto.request.CreateHomestayRequest;
import com.homestaywithme.app.domain.homestay.usecase.gethomestaybyid.GetHomestayByIdUseCase;
import com.homestaywithme.app.domain.homestay.usecase.gethomestayprices.GetHomestayPricesUseCase;
import com.homestaywithme.app.domain.homestay.usecase.gethomestayprices.dto.request.GetHomestayPricesRequest;
import com.homestaywithme.app.domain.homestay.usecase.searchhomestay.SearchHomestayUseCase;
import com.homestaywithme.app.domain.homestay.usecase.searchhomestay.dto.request.SearchHomestayRequest;
import com.homestaywithme.app.domain.homestay.usecase.updatehomestay.dto.request.UpdateHomestayRequest;
import com.homestaywithme.app.domain.homestay.usecase.createhomestay.CreateHomestayUseCase;
import com.homestaywithme.app.domain.homestay.usecase.updatehomestayprice.UpdateHomestayPriceUseCase;
import com.homestaywithme.app.domain.homestay.usecase.updatehomestay.UpdateHomestayUseCase;
import com.homestaywithme.app.domain.homestay.usecase.updatehomestayprice.dto.request.UpdateHomestayPriceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("api/homestays")
@RequiredArgsConstructor
public class HomestayController {
    private final ResponseService responseService;
    private final CreateHomestayUseCase createHomestayUseCase;
    private final UpdateHomestayUseCase updateHomestayUseCase;
    private final UpdateHomestayPriceUseCase updateHomestayPriceUseCase;
    private final GetHomestayPricesUseCase getHomestayPricesUseCase;
    private final SearchHomestayUseCase searchHomestayUseCase;
    private final GetHomestayByIdUseCase getHomestayByIdUseCase;

    @PostMapping
    public Response createHomestay(@RequestBody CreateHomestayRequest request) {
        return createHomestayUseCase.createHomestay(request);
    }

    @PutMapping("/{id}")
    public Response updateHomestay(@PathVariable("id") Long id,
                                   @RequestBody UpdateHomestayRequest request) {
        return responseService.responseSuccessWithPayload(updateHomestayUseCase.updateHomestay(id, request));
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

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public Response getHomestayDetailById(@PathVariable("id") Long id) {
        return responseService.responseSuccessWithPayload(getHomestayByIdUseCase.getHomestayDetailById(id));
    }
}
