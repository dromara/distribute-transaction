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
package io.transaction.xa.config;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author binghe
 * @version 1.0.0
 * @description 整合MyBatis的第二个配置
 */

@Configuration
@MapperScan(basePackages = "io.transaction.xa.mapper2", sqlSessionTemplateRef = "account2SqlSessionTemplate")
public class MyBatisConfig2 {

    // 配置数据源
    @Bean(name = "account2DataSource")
    public DataSource account2DataSource(DBConfig2 dbConfig2) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dbConfig2.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(dbConfig2.getPassword());
        mysqlXaDataSource.setUser(dbConfig2.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("account2DataSource");

        xaDataSource.setMinPoolSize(dbConfig2.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig2.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig2.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig2.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig2.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dbConfig2.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfig2.getMaxIdleTime());
        xaDataSource.setTestQuery(dbConfig2.getTestQuery());
        return xaDataSource;
    }

    @Bean(name = "account2SqlSessionFactory")
    public SqlSessionFactory account2SqlSessionFactory(@Qualifier("account2DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "account2SqlSessionTemplate")
    public SqlSessionTemplate account2SqlSessionTemplate(
            @Qualifier("account2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
