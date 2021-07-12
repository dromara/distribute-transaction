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
package io.transaction.xa;

import io.transaction.xa.config.DBConfig1;
import io.transaction.xa.config.DBConfig2;
import io.transaction.xa.service.UserAccountService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

/**
 * @author binghe
 * @version 1.0.0
 * @description 项目启动类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(value = {DBConfig1.class, DBConfig2.class})
@MapperScan(value = { "io.transaction.xa.mapper1","io.transaction.xa.mapper2"})
@EnableTransactionManagement(proxyTargetClass = true)
public class TxXaStarter {

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(TxXaStarter.class, args);
        UserAccountService userAccountService = context.getBean(UserAccountService.class);
        userAccountService.transferAccounts("1001", "1002", BigDecimal.valueOf(100));
    }
}
