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
package io.transaction.notifymsg.pay.controller;

import io.transaction.notifymsg.pay.entity.PayInfo;
import io.transaction.notifymsg.pay.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binghe
 * @version 1.0.0
 * @description
 */
@RestController
public class PayInfoController {
    @Autowired
    private PayInfoService payInfoService;

    //充值
    @GetMapping(value = "/pay_account")
    public PayInfo pay(PayInfo payInfo){
        //生成事务编号
        return payInfoService.savePayInfo(payInfo);
    }

    //查询充值结果
    @GetMapping(value = "/query/payresult/{txNo}")
    public PayInfo payResult(@PathVariable("txNo") String txNo){
        return payInfoService.getPayInfoByTxNo(txNo);
    }
}
