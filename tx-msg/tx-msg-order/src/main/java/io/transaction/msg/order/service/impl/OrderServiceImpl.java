/**
 * Copyright 2020-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.transaction.msg.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.transaction.msg.order.entity.Order;
import io.transaction.msg.order.mapper.OrderMapper;
import io.transaction.msg.order.service.OrderService;
import io.transaction.msg.order.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author binghe
 * @version 1.0.0
 * @description 订单微服务Service实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrderAndSaveTxNo(TxMessage txMessage) {
        Integer existsTx = orderMapper.isExistsTx(txMessage.getTxNo());
        if(existsTx != null){
            log.info("订单微服务已经执行过事务,商品id为:{}，事务编号为:{}",txMessage.getProductId(), txMessage.getTxNo());
            return;
        }
        //生成订单
        Order order = new Order();
        order.setId(System.currentTimeMillis());
        order.setCreateTime(new Date());
        order.setOrderNo(String.valueOf(System.currentTimeMillis()));
        order.setPayCount(txMessage.getPayCount());
        order.setProductId(txMessage.getProductId());
        orderMapper.saveOrder(order);

        //添加事务日志
        orderMapper.saveTxLog(txMessage.getTxNo());
    }

    @Override
    public void submitOrder(Long productId, Integer payCount) {
        //生成全局分布式序列号
        String txNo = UUID.randomUUID().toString();
        TxMessage txMessage = new TxMessage(productId, payCount, txNo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txMessage", txMessage);
        Message<String> message = MessageBuilder.withPayload(jsonObject.toJSONString()).build();
        //发送一条事务消息
        rocketMQTemplate.sendMessageInTransaction("tx_order_group", "topic_txmsg", message, null);
    }
}
