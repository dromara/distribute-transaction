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
package io.transaction.msg.stock.mapper;

import io.transaction.msg.stock.entity.Stock;
import org.apache.ibatis.annotations.Param;

/**
 * @author binghe
 * @version 1.0.0
 * @description 库存Mapper
 */
public interface StockMapper {

    Stock getStockById(@Param("id") Long id);

    /**
     * 根据商品id获取库存信息
     */
    Stock getStockByProductId(@Param("productId") Long productId);

    /**
     * 修改商品库存
     */
    int updateTotalCountById(@Param("count") Integer count, @Param("id") Long id);


    /**
     * 检查是否存在指定事务编号的事务记录，如果存在则说明已经执行过
     * 用于幂等操作
     */
    Integer isExistsTx(@Param("txNo") String txNo);

    /**
     * 保存事务记录
     */
    void saveTxLog(@Param("txNo") String txNo);
}
