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
package io.transaction.notifymsg.pay.service.impl;

import io.transaction.notifymsg.pay.entity.PayInfo;
import io.transaction.notifymsg.pay.mapper.PayInfoMapper;
import io.transaction.notifymsg.pay.service.PayInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author binghe
 * @version 1.0.0
 * @description 充值Service实现类
 */
@Slf4j
@Service
public class PayInfoServiceImpl implements PayInfoService {
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public PayInfo savePayInfo(PayInfo payInfo) {
        payInfo.setTxNo(UUID.randomUUID().toString());
        payInfo.setPayResult("success");
        payInfo.setPayTime(new Date());
        int count = payInfoMapper.savePayInfo(payInfo);
        //充值信息保存成功
        if(count > 0){
            log.info("充值微服务向账户微服务发送结果消息");
            //发送消息通知账户微服务
            rocketMQTemplate.convertAndSend("topic_nofitymsg", payInfo);
            return payInfo;
        }
        return null;
    }

    @Override
    public PayInfo getPayInfoByTxNo(String txNo) {
        return payInfoMapper.getPayInfoByTxNo(txNo);
    }
}
