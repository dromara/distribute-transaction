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
package io.transaction.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author binghe
 * @version 1.0.0
 * @description 商品dao
 */
@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateProductStockCountById(Integer stockCount, Long id){
        String sql = "update product_info set stock_count = stock_count - ? where id = ?";
        return jdbcTemplate.update(sql, stockCount, id);
    }
}
