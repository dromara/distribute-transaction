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
package io.transaction.tcc.bank01.service.impl;

import io.transaction.tcc.bank01.service.UserAccountBank01Service;
import io.transaction.tcc.common.api.UserAccountBank02Service;
import io.transaction.tcc.common.dto.UserAccountDto;
import io.transaction.tcc.common.entity.UserAccount;
import io.transaction.tcc.common.mapper.UserAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author binghe
 * @version 1.0.0
 * @description 转出银行微服务Service实现类
 */
@Slf4j
@Service("userAccountBank01Service")
public class UserAccountBank01ServiceImpl implements UserAccountBank01Service {
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired(required = false)
    private UserAccountBank02Service userAccountBank02Service;

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public void transferAmount(UserAccountDto userAccountDto) {
        String txNo = userAccountDto.getTxNo();
        log.info("执行bank01的Try方法，事务id为:{}", txNo);
        if(userAccountMapper.existsTryLog(txNo)!= null){
            log.info("bank01已经执行过Try方法, txNo:{}", txNo);
            return;
        }
        //悬挂处理
        if(userAccountMapper.existsConfirmLog(txNo) != null || userAccountMapper.existsCancelLog(txNo) != null){
            log.info("bank01的Confirm方法或者Cancel方法已经执行过，txNo:{}", txNo);
            return;
        }
        UserAccount sourceAccount = userAccountMapper.getUserAccountByAccountNo(userAccountDto.getSourceAccountNo());
        if(sourceAccount == null){
            throw new RuntimeException("不存在转出账户");
        }
        if(sourceAccount.getAccountBalance().compareTo(userAccountDto.getAmount()) < 0){
            throw new RuntimeException("账户余额不足");
        }
        UserAccount targetAccount = userAccountBank02Service.getUserAccountByAccountNo(userAccountDto.getTargetAccountNo());
        if(targetAccount == null){
            throw new RuntimeException("不存在转入账户");
        }
        userAccountMapper.saveTryLog(txNo);
        userAccountMapper.updateUserAccountBalanceBank01(userAccountDto.getAmount(), userAccountDto.getSourceAccountNo());

        userAccountBank02Service.transferAmountToBank2(userAccountDto);
    }


    public void confirmMethod(UserAccountDto userAccountDto){
        String txNo = userAccountDto.getTxNo();
        log.info("执行bank01的Confirm方法，事务id为:{}", txNo);
        if(userAccountMapper.existsConfirmLog(txNo) != null){
            log.info("bank01已经执行过Confirm方法, txNo:{}", txNo);
            return;
        }
        userAccountMapper.saveConfirmLog(txNo);
        userAccountMapper.confirmUserAccountBalanceBank01(userAccountDto.getAmount(), userAccountDto.getSourceAccountNo());
    }

    public void cancelMethod(UserAccountDto userAccountDto){
        String txNo = userAccountDto.getTxNo();
        log.info("执行bank01的Cancel方法，事务id为:{}", txNo);
        if(userAccountMapper.existsCancelLog(txNo) != null){
            log.info("bank01已经执行过Cancel方法, txNo:{}", txNo);
            return;
        }
        userAccountMapper.saveCancelLog(txNo);
        userAccountMapper.cancelUserAccountBalanceBank01(userAccountDto.getAmount(), userAccountDto.getSourceAccountNo());

    }
}
