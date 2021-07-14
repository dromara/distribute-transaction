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
package io.transaction.tcc.common.mapper;

import io.transaction.tcc.common.entity.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 账户Mapper接口
 */
public interface UserAccountMapper {

    /**
     * 获取指定账户的余额
     */
    UserAccount getUserAccountByAccountNo(@Param("accountNo") String accountNo);

    /**
     * 更新转出账户余额
     */
    int updateUserAccountBalanceBank01(@Param("amount") BigDecimal amount, @Param("accountNo") String accountNo);

    /**
     * 转出账户余额确认接口
     */
    int confirmUserAccountBalanceBank01(@Param("amount") BigDecimal amount, @Param("accountNo") String accountNo);

    /**
     * 转出账户余额取消接口
     */
    int cancelUserAccountBalanceBank01(@Param("amount") BigDecimal amount, @Param("accountNo") String accountNo);


    /**
     * 更新转入账户余额
     */
    int updateUserAccountBalanceBank02(@Param("amount") BigDecimal amount, @Param("accountNo") String accountNo);

    /**
     * 转入账户余额确认接口
     */
    int confirmUserAccountBalanceBank02(@Param("amount") BigDecimal amount, @Param("accountNo") String accountNo);

    /**
     * 转入账户余额取消接口
     */
    int cancelUserAccountBalanceBank02(@Param("amount") BigDecimal amount, @Param("accountNo") String accountNo);

    /**
     * 保存Try操作事务日志
     */
    int saveTryLog(@Param("txNo") String txNo);

    /**
     * 检查是否存在Try操作日志，用于幂等
     */
    Integer existsTryLog(@Param("txNo") String txNo);


    /**
     * 保存Confirm操作事务日志
     */
    int saveConfirmLog(@Param("txNo") String txNo);

    /**
     * 检查是否存在Confirm操作日志，用于幂等
     */
    Integer existsConfirmLog(@Param("txNo") String txNo);

    /**
     * 保存Cancel操作事务日志
     */
    int saveCancelLog(@Param("txNo") String txNo);

    /**
     * 检查是否存在Cancel操作日志，用于幂等
     */
    Integer existsCancelLog(@Param("txNo") String txNo);

}
