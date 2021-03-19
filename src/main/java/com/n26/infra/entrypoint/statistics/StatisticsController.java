package com.n26.infra.entrypoint.statistics;

import com.n26.domain.usecase.statistics.getstatistics.GetStatisticsResponseModel;
import com.n26.domain.usecase.statistics.getstatistics.GetStatisticsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;

@RestController
@RequestMapping(path = "/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final GetStatisticsUseCase getStatisticsUseCase;

    @GetMapping
    public ResponseEntity<GetStatisticsResponse> getStatistics() {
        GetStatisticsResponseModel responseModel = getStatisticsUseCase.getStatistics();

        return ResponseEntity.ok(new GetStatisticsResponse(
                responseModel.getSum().setScale(2, RoundingMode.HALF_UP).toString(),
                responseModel.getAvg().setScale(2, RoundingMode.HALF_UP).toString(),
                responseModel.getMax().setScale(2, RoundingMode.HALF_UP).toString(),
                responseModel.getMin().setScale(2, RoundingMode.HALF_UP).toString(),
                responseModel.getCount()
        ));
    }

}