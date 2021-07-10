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
package io.transaction.notifymsg.pay.mapper;

import io.transaction.notifymsg.pay.entity.PayInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author binghe
 * @version 1.0.0
 * @description 充值Mapper
 */
public interface PayInfoMapper {

    /**
     * 查询是否存在充值记录
     */
    Integer isExistsPayInfo(@Param("txNo") String txNo);

    /**
     * 保存充值记录
     */
    int savePayInfo(@Param("payInfo") PayInfo payInfo);

    /**
     * 查询指定的充值信息
     */
    PayInfo getPayInfoByTxNo(@Param("txNo") String txNo);
}
