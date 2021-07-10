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
package io.transaction.notifymsg.account.service.impl;

import io.transaction.notifymsg.account.entity.PayInfo;
import io.transaction.notifymsg.account.mapper.AccountInfoMapper;
import io.transaction.notifymsg.account.mapper.PayInfoMapper;
import io.transaction.notifymsg.account.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 账户Service实现类
 */
@Slf4j
@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(PayInfo payInfo) {
        if(payInfoMapper.isExistsPayInfo(payInfo.getTxNo()) != null){
            log.info("账户微服务已经处理过当前事务...");
            return;
        }
        //更新账户余额
        accountInfoMapper.updateAccoutBalanceByAccountNo(payInfo.getPayAmount(), payInfo.getAccountNo());
        //保存充值记录
        payInfoMapper.savePayInfo(payInfo);
    }

    @Override
    public PayInfo queryPayResult(String txNo) {
        //TODO 待实现
        return null;
    }
}
