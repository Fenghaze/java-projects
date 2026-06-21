package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
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
    @Autowired
    private UserMapper userMapper;


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

    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        List<String> dateList = new ArrayList<>();
        List<String> totalUserList = new ArrayList<>();
        List<String> newUserList = new ArrayList<>();
        while (!begin.isEqual(end.plusDays(1))) {
            dateList.add(begin.toString());
            totalUserList.add(String.valueOf(userMapper.totalUserStatistics(
                null,
                LocalDateTime.of(begin, LocalTime.MAX)
            )));
            newUserList.add(String.valueOf(userMapper.totalUserStatistics(
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(begin, LocalTime.MAX)
            )));
            begin = begin.plusDays(1);
        }
        UserReportVO userReportVO = UserReportVO.builder()
            .dateList(String.join(",", dateList))
            .totalUserList(String.join(",", totalUserList))
            .newUserList(String.join(",", newUserList))
            .build();
        return userReportVO;
    }
}
