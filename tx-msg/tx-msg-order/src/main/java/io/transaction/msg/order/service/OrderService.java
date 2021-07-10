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
package io.transaction.msg.order.service;

import io.transaction.msg.order.entity.Order;
import io.transaction.msg.order.tx.TxMessage;
import org.apache.ibatis.annotations.Param;

/**
 * @author binghe
 * @version 1.0.0
 * @description 订单Service
 */
public interface OrderService {

    /**
     * 提交订单同时保存事务信息
     */
    void submitOrderAndSaveTxNo(TxMessage txMessage);

    /**
     * 提交订单
     * @param productId 商品id
     * @param payCount 购买数量
     */
    void submitOrder(Long productId, Integer payCount);
}
