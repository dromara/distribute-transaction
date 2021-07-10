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
package io.transaction.notifymsg.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 账户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 3159662335364762944L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

}
