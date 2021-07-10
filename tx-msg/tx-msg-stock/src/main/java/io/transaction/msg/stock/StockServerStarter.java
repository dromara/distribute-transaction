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
package io.transaction.msg.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author binghe
 * @version 1.0.0
 * @description 库存微服务启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"io.transaction.msg"})
@MapperScan(value = { "io.transaction.msg.stock.mapper" })
@EnableTransactionManagement(proxyTargetClass = true)
public class StockServerStarter {
    public static void main(String[] args) {
        SpringApplication.run(StockServerStarter.class, args);
    }
}
