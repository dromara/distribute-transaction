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
package io.transaction.notifymsg.account.message;

import com.alibaba.fastjson.JSONObject;
import io.transaction.notifymsg.account.entity.PayInfo;
import io.transaction.notifymsg.account.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author binghe
 * @version 1.0.0
 * @description 监听通知消息
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_account", topic = "topic_nofitymsg")
public class NotifyMsgAccountListener implements RocketMQListener<PayInfo> {
    @Autowired
    private AccountInfoService accountInfoService;
    @Override
    public void onMessage(PayInfo payInfo) {
        log.info("账户微服务收到RocketMQ的消息:{}", JSONObject.toJSONString(payInfo));
        //如果是充值成功，则修改账户余额
        if("success".equals(payInfo.getPayResult())){
            accountInfoService.updateAccountBalance(payInfo);
        }
        log.info("更新账户余额完毕:{}", JSONObject.toJSONString(payInfo));
    }
}
