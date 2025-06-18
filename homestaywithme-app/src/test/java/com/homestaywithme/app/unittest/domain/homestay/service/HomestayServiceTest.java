package com.homestaywithme.app.unittest.domain.homestay.service;

import com.homestaywithme.app.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.app.domain.homestay.entity.Homestay;
import com.homestaywithme.app.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.app.domain.homestay.service.HomestayService;
import com.homestaywithme.app.domain.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class HomestayServiceTest {
    @Mock
    private HomestayRepository homestayRepository;

    @InjectMocks
    private HomestayService homestayService;

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void findHomestayById_NotFound_ShouldThrowException() {
        // Arrange
        Mockito.when(homestayRepository.findById(any())).thenReturn(Optional.empty());

        // Act
        var businessException = assertThrows(BusinessException.class, () -> homestayService.findHomestayById(1L));

        // Assert
        assertEquals(businessException.getMessage(), String.format(HomestayExceptionMessage.HOMESTAY_NOT_FOUND_FORMAT, 1L));
    }

    @Test
    void findHomestayById_FoundHomestay_ShouldWork() {
        // Arrange
        var homestay = new Homestay();
        homestay.setId(1L);
        homestay.setName("homestay");
        Mockito.when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));

        // Act
        var foundHomestay = homestayRepository.findById(1L).orElse(null);

        // Assert
        assert foundHomestay != null;
        assertThat(foundHomestay.getId()).isEqualTo(homestay.getId());
        assertThat(foundHomestay.getName()).isEqualTo(homestay.getName());
    }
}
