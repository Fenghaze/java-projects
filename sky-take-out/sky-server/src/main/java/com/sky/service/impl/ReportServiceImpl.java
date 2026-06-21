package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
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
    @Autowired
    private OrderDetailMapper orderDetailMapper;


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

    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        List<String> dateList = new ArrayList<>();
        List<String> orderCountList = new ArrayList<>();
        List<String> validOrderCountList = new ArrayList<>();
        Integer totalOrderCount = 0;
        Integer validOrderCount = 0;
        Double orderCompletionRate = 0.0;
        // 查询每日数据
        while (!begin.isEqual(end.plusDays(1))) {
            dateList.add(begin.toString());
            Integer dailyOrderCount = orderMapper.orderCountStatistics(
                null,
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(begin, LocalTime.MAX)
            );
            Integer dailyValidOrderCount = orderMapper.orderCountStatistics(Orders.COMPLETED,
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(begin, LocalTime.MAX)
            );
            // 查询订单数
            orderCountList.add(String.valueOf(dailyOrderCount));
            // 查询订单完成数
            validOrderCountList.add(String.valueOf(dailyValidOrderCount));
            // 累计每日数据
            totalOrderCount += dailyOrderCount;
            validOrderCount += dailyValidOrderCount;
            begin = begin.plusDays(1);
        }
        // 计算订单完成率
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        OrderReportVO orderReportVO = OrderReportVO.builder()
            .dateList(String.join(",", dateList))
            .orderCountList(String.join(",", orderCountList))
            .validOrderCountList(String.join(",", validOrderCountList))
            .totalOrderCount(totalOrderCount)
            .validOrderCount(validOrderCount)
            .orderCompletionRate(orderCompletionRate)
            .build();
        return orderReportVO;
    }

    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        List<String> nameList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();
        List<GoodsSalesDTO> goodsSalesDTOS = orderDetailMapper.getSalesTop10(
            Orders.COMPLETED,
            LocalDateTime.of(begin, LocalTime.MIN),
            LocalDateTime.of(end, LocalTime.MAX)
        );
        for (GoodsSalesDTO goodsSalesDTO : goodsSalesDTOS) {
            nameList.add(goodsSalesDTO.getName());
            numberList.add(String.valueOf(goodsSalesDTO.getNumber()));
        }
        begin = begin.plusDays(1);
        SalesTop10ReportVO salesTop10ReportVO = SalesTop10ReportVO.builder()
            .nameList(String.join(",", nameList))
            .numberList(String.join(",", numberList))
            .build();
        return salesTop10ReportVO;
    }
}
