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
package io.transaction.xa.service.impl;

import io.transaction.xa.mapper1.UserAccount1Mapper;
import io.transaction.xa.mapper2.UserAccount2Mapper;
import io.transaction.xa.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 转账的Service实现
 */
@Slf4j
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccount1Mapper userAccount1Mapper;

    @Autowired
    private UserAccount2Mapper userAccount2Mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferAccounts(String sourceAccountNo, String targetSourceNo, BigDecimal transferAmount) {
        log.info("开始执行转账操作, sourceAccountNo:{}, targetSourceNo:{}, transferAmount:{}", sourceAccountNo, targetSourceNo, transferAmount);
        BigDecimal accountBalance = userAccount1Mapper.getAccountBalance(sourceAccountNo);
        if(accountBalance.compareTo(transferAmount) < 0){
            throw new RuntimeException("转账余额不足");
        }
        userAccount1Mapper.updateAccountBalance(transferAmount.negate(), sourceAccountNo);
        //int i = 1 / 0;
        userAccount2Mapper.updateAccountBalance(transferAmount, targetSourceNo);
        log.info("转账操作执行成功...");
    }
}
