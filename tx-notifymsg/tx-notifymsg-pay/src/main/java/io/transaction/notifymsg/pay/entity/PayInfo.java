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
package io.transaction.notifymsg.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 充值记录信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayInfo implements Serializable {
    private static final long serialVersionUID = -1971185546761595695L;
    /**
     * 充值记录主键
     */
    private String txNo;

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 充值金额
     */
    private BigDecimal payAmount;

    /**
     * 充值时间
     */
    private Date payTime;

    /**
     * 充值结果
     */
    private String payResult;
}
