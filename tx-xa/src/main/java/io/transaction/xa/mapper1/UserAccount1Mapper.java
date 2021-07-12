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
package io.transaction.xa.mapper1;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 用户账户的Mapper接口
 */
public interface UserAccount1Mapper {

    /**
     * 更新账户余额
     */
    int updateAccountBalance(@Param("accountBalance") BigDecimal accountBalance, @Param("accountNo") String accountNo);

    /**
     * 获取账户余额
     */
    BigDecimal getAccountBalance(@Param("accountNo") String accountNo);
}
