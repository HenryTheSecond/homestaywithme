package com.homestaywithme.domain.homestay.usecase.searchhomestay;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.homestay.usecase.searchhomestay.constant.SearchHomestaySort;
import com.homestaywithme.domain.homestay.usecase.searchhomestay.dto.request.SearchHomestayRequest;
import com.homestaywithme.domain.shared.constant.Order;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.constant.StringConstant;
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

    @Transactional(readOnly = true)
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
            case "", "distance" -> SearchHomestaySort.DISTANCE.getValue();
            case "price" -> SearchHomestaySort.PRICE.getValue();
            default -> throw new BusinessException(String.format(HomestayExceptionMessage.SORT_NOT_SUPPORTED_FORMAT, sort),
                    ResponseCode.BAD_REQUEST);
        };

        return sort + StringConstant.SPACE + validateAndGetOrder(order);
    }

    private String validateAndGetOrder(String order) {
        if(order == null || order.isBlank() || order.equalsIgnoreCase(Order.ASC.getValue())) {
            return Order.ASC.getValue();
        }
        else if(order.equalsIgnoreCase(Order.DESC.getValue())) {
            return Order.DESC.getValue();
        }

        throw new BusinessException(String.format(HomestayExceptionMessage.ORDER_NOT_SUPPORTED_FORMAT, order),
                ResponseCode.BAD_REQUEST);
    }
}
