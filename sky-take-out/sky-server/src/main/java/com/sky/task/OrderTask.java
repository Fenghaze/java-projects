package com.sky.task;

import com.sky.constant.MessageConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 每分钟检查一次订单是否超时
     */
    @Scheduled(cron = "0 * * * * ?")
    public void handleOvertimeOrder () {
        log.info("定时任务：超时订单检查");
        LocalDateTime time = LocalDateTime.now().minusMinutes(15);
        List<Orders> orders = orderMapper.getOvertimeOrders(Orders.PENDING_PAYMENT, time);
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason(MessageConstant.PAYMENT_NOT_COMPLETED);
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 每天1点检查一次派送订单情况
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void handleDeliveryOrder () {
        log.info("定时任务：派送订单检查");
        LocalDateTime time = LocalDateTime.now().minusHours(1);
        List<Orders> orders = orderMapper.getOvertimeOrders(Orders.DELIVERY_IN_PROGRESS, time);
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
