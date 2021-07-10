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
package io.transaction.msg.stock.service.impl;

import io.transaction.msg.stock.entity.Stock;
import io.transaction.msg.stock.mapper.StockMapper;
import io.transaction.msg.stock.service.StockService;
import io.transaction.msg.stock.tx.TxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;

    @Override
    public Stock getStockById(Long id) {
        return stockMapper.getStockById(id);
    }

    @Override
    public void decreaseStock(TxMessage txMessage) {
        log.info("库存微服务执行本地事务,商品id:{}, 购买数量:{}", txMessage.getProductId(), txMessage.getPayCount());
        //检查是否执行过事务
        Integer exists = stockMapper.isExistsTx(txMessage.getTxNo());
        if(exists != null){
            log.info("库存微服务已经执行过事务,事务编号为:{}", txMessage.getTxNo());
        }
        Stock stock = stockMapper.getStockByProductId(txMessage.getProductId());
        if(stock.getTotalCount() < txMessage.getPayCount()){
            throw  new RuntimeException("库存不足");
        }
        stockMapper.updateTotalCountById(txMessage.getPayCount(), stock.getId());
        //记录事务日志
        stockMapper.saveTxLog(txMessage.getTxNo());
    }
}
