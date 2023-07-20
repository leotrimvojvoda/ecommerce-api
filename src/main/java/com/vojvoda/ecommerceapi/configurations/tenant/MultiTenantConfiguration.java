package com.vojvoda.ecommerceapi.configurations.tenant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Configuration
public class MultiTenantConfiguration {

    @Value("${default-tenant}")
    private String defaultTenant;

    @Bean
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        List<Properties> propertiesList = getTenantProperties();

        for(Properties properties : propertiesList){
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            String tenantId = properties.getProperty("name");

            dataSourceBuilder.driverClassName(properties.getProperty("datasource.driver-class-name"));
            dataSourceBuilder.username(properties.getProperty("datasource.username"));
            dataSourceBuilder.password(properties.getProperty("datasource.password"));
            dataSourceBuilder.url(properties.getProperty("datasource.url"));
            resolvedDataSources.put(tenantId, dataSourceBuilder.build());
        }

        AbstractRoutingDataSource dataSource = new MultiTenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

    public List<Properties> getTenantProperties() {
        File[] files = Paths.get("src/main/resources/tenants").toFile().listFiles();
        List<Properties> propertiesList = new ArrayList<>();

        if (files != null) {

            for (File propertyFile : files) {
                try {
                    Properties tenantProperties = new Properties();
                    tenantProperties.load(new FileInputStream(propertyFile));
                    propertiesList.add(tenantProperties);
                } catch (IOException ex) {
                    throw new RuntimeException("Problem in tenant datasource:" + ex);
                }
            }
        }
        return propertiesList;
    }
}
