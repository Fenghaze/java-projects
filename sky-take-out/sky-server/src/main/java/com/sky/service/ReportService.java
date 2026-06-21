package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import java.time.LocalDate;

public interface ReportService {

    /**
     * 营业额统计
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户统计：统计用户总量、新增用户
     * @param begin
     * @param end
     * @return
     */
    UserReportVO userStatistics(LocalDate begin, LocalDate end);
}
