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
package io.transaction.tcc.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 用户账户的Dto类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto implements Serializable {

    private static final long serialVersionUID = 3361105512695088121L;

    /**
     * 自定义事务编号
     */
    private String txNo;

    /**
     * 转出账户
     */
    private String sourceAccountNo;

    /**
     * 转入账户
     */
    private String targetAccountNo;
    /**
     * 金额
     */
    private BigDecimal amount;
}
