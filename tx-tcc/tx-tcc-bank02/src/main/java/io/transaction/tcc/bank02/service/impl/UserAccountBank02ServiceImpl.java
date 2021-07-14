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
package io.transaction.tcc.bank02.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.transaction.tcc.common.api.UserAccountBank02Service;
import io.transaction.tcc.common.dto.UserAccountDto;
import io.transaction.tcc.common.entity.UserAccount;
import io.transaction.tcc.common.mapper.UserAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author binghe
 * @version 1.0.0
 * @description
 */
@Slf4j
@Service("userAccountBank02Service")
public class UserAccountBank02ServiceImpl implements UserAccountBank02Service {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public void transferAmountToBank2(UserAccountDto userAccountDto) {
        String txNo = userAccountDto.getTxNo();
        log.info("执行bank02的Try方法，事务id为:{}, 参数为:{}", txNo, JSONObject.toJSONString(userAccountDto));
        //幂等处理
        if(userAccountMapper.existsTryLog(txNo) != null){
            log.info("bank02已经执行过try方法, txNo:{}", txNo);
            return;
        }
        //悬挂处理
        if(userAccountMapper.existsConfirmLog(txNo) != null || userAccountMapper.existsCancelLog(txNo) != null){
            log.info("bank02的Confirm方法或者Cancel方法已经执行过，txNo:{}", txNo);
            return;
        }
        UserAccount userAccount = userAccountMapper.getUserAccountByAccountNo(userAccountDto.getTargetAccountNo());
        if(userAccount == null){
            throw new RuntimeException("不存在此账户");
        }
        userAccountMapper.saveTryLog(txNo);
        userAccountMapper.updateUserAccountBalanceBank02(userAccountDto.getAmount(), userAccountDto.getTargetAccountNo());
    }

    @Override
    public UserAccount getUserAccountByAccountNo(String accountNo) {
        return userAccountMapper.getUserAccountByAccountNo(accountNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmMethod(UserAccountDto userAccountDto){
        String txNo = userAccountDto.getTxNo();
        log.info("执行bank02的Confirm方法，事务id为:{}, 参数为:{}", txNo, JSONObject.toJSONString(userAccountDto));
        if(userAccountMapper.existsConfirmLog(txNo) != null){
            log.info("bank02已经执行过Confirm方法, txNO:{}", txNo);
            return;
        }
        userAccountMapper.saveConfirmLog(txNo);
        userAccountMapper.confirmUserAccountBalanceBank02(userAccountDto.getAmount(), userAccountDto.getTargetAccountNo());
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelMethod(UserAccountDto userAccountDto){
        String txNo = userAccountDto.getTxNo();
        log.info("执行bank02的Confirm方法，事务id:{}, 参数为:{}", txNo, JSONObject.toJSONString(userAccountDto));
        if(userAccountMapper.existsCancelLog(txNo) != null){
            log.info("bank02已经执行过Cancel方法, txNo:{}", txNo);
            return;
        }
        userAccountMapper.saveCancelLog(txNo);
        userAccountMapper.cancelUserAccountBalanceBank02(userAccountDto.getAmount(), userAccountDto.getTargetAccountNo());
    }
}
