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
package io.transaction.msg.order.tx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author binghe
 * @version 1.0.0
 * @description 分布式事务
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxMessage implements Serializable {

    private static final long serialVersionUID = -4704980150056885074L;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品购买数量
     */
    private Integer payCount;

    /**
     * 全局事务编号
     */
    private String txNo;
}
