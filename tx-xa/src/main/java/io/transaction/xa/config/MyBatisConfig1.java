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
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author binghe
 * @version 1.0.0
 * @description 整合MyBatis的第一个配置
 */
@Configuration
@MapperScan(basePackages = "io.transaction.xa.mapper1", sqlSessionTemplateRef = "account1SqlSessionTemplate")
public class MyBatisConfig1 {

    // 配置数据源
    @Primary
    @Bean(name = "account1DataSource")
    public DataSource account1DataSource(DBConfig1 dbConfig1) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dbConfig1.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(dbConfig1.getPassword());
        mysqlXaDataSource.setUser(dbConfig1.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("account1DataSource");

        xaDataSource.setMinPoolSize(dbConfig1.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig1.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig1.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig1.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig1.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dbConfig1.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfig1.getMaxIdleTime());
        xaDataSource.setTestQuery(dbConfig1.getTestQuery());
        return xaDataSource;
    }

    @Primary
    @Bean(name = "accout1SqlSessionFactory")
    public SqlSessionFactory accout1SqlSessionFactory(@Qualifier("account1DataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Primary
    @Bean(name = "account1SqlSessionTemplate")
    public SqlSessionTemplate account1SqlSessionTemplate(
            @Qualifier("accout1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
