package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        List<String> dateList = new ArrayList<>();
        List<String> turnoverList = new ArrayList<>();
        while (!begin.isEqual(end.plusDays(1))) {
            dateList.add(begin.toString());
            Double turnover = orderMapper.turnoverStatistics(
                Orders.COMPLETED,
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(begin, LocalTime.MAX)
            );
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(String.valueOf(turnover));
            begin = begin.plusDays(1);
        }
        TurnoverReportVO turnoverReportVO = TurnoverReportVO.builder()
                .dateList(String.join(",", dateList))
                .turnoverList(String.join(",", turnoverList))
                .build();
        return turnoverReportVO;
    }
}
