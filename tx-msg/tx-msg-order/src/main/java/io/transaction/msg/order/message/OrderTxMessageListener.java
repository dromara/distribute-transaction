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
package io.transaction.msg.order.message;

import com.alibaba.fastjson.JSONObject;
import io.transaction.msg.order.mapper.OrderMapper;
import io.transaction.msg.order.service.OrderService;
import io.transaction.msg.order.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author binghe
 * @version 1.0.0
 * @description 监听事务消息
 */

@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx_order_group")
public class OrderTxMessageListener implements RocketMQLocalTransactionListener {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object obj) {
        try{
            log.info("订单微服务执行本地事务");
            TxMessage txMessage = this.getTxMessage(msg);
            //执行本地事务
            orderService.submitOrderAndSaveTxNo(txMessage);
            //提交事务
            log.info("订单微服务提交事务");
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            e.printStackTrace();
            //异常回滚事务
            log.info("订单微服务回滚事务");
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("订单微服务查询本地事务");
        TxMessage txMessage = this.getTxMessage(msg);
        Integer exists = orderMapper.isExistsTx(txMessage.getTxNo());
        if(exists != null){
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    private TxMessage getTxMessage(Message msg){
        String messageString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String txStr = jsonObject.getString("txMessage");
        return JSONObject.parseObject(txStr, TxMessage.class);
    }
}
