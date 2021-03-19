package com.n26.infra.entrypoint.statistics;

import com.n26.domain.usecase.statistics.getstatistics.GetStatisticsResponseModel;
import com.n26.domain.usecase.statistics.getstatistics.GetStatisticsUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {StatisticsController.class})
public class StatisticsControllerTest {

    private static final String URI = "/statistics";

    @MockBean
    private GetStatisticsUseCase getStatisticsUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStatistics() throws Exception {
        when(getStatisticsUseCase.getStatistics()).thenReturn(new GetStatisticsResponseModel(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                1L
        ));

        ResultActions resultActions = mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON_UTF8));
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.sum", is("1.00")))
                .andExpect(jsonPath("$.avg", is("1.00")))
                .andExpect(jsonPath("$.max", is("1.00")))
                .andExpect(jsonPath("$.min", is("1.00")))
                .andExpect(jsonPath("$.count", is(1)));
    }

}