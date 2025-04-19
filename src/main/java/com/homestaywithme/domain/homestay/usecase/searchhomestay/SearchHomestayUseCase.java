package com.homestaywithme.domain.homestay.usecase.searchhomestay;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.homestay.usecase.searchhomestay.dto.request.SearchHomestayRequest;
import com.homestaywithme.domain.shared.exception.BusinessException;
import com.homestaywithme.domain.shared.model.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchHomestayUseCase {
    private final ResponseService responseService;
    private final HomestayRepository homestayRepository;

    @Autowired
    public SearchHomestayUseCase(ResponseService responseService, HomestayRepository homestayRepository) {
        this.responseService = responseService;
        this.homestayRepository = homestayRepository;
    }

    @Transactional
    public Response searchHomestay(SearchHomestayRequest request) {
        var homestayPage = homestayRepository.searchHomestay(request.getLongitude(),
                request.getLatitude(),
                request.getDistance(),
                request.getFrom(),
                request.getTo(),
                validateAndGetOrderBy(request.getSort(), request.getOrder()),
                PageRequest.of(request.getPage() - 1, request.getPageSize()));

        return responseService.responseSuccessWithPayload(new PaginationResult<>(homestayPage.get().toList(), homestayPage.getTotalElements()));
    }

    private String validateAndGetOrderBy(String sort, String order) {
        sort = switch (sort) {
            case "", "distance" -> "h.geom <-> d.geom";
            case "price" -> "totalPrice";
            default -> throw new BusinessException("Not supported sort by " + sort);
        };

        return sort + " " + validateAndGetOrder(order);
    }

    private String validateAndGetOrder(String order) {
        if(order == null || order.isBlank() || order.equalsIgnoreCase("asc")) {
            return "ASC";
        }
        else if(order.equalsIgnoreCase("desc")) {
            return "DESC";
        }

        throw new BusinessException("Not supported order " + order);
    }
}
