/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.controller.gateway.GatewayFlowRuleController;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * @author lbj
 * @since 1.8.1
 */
@Configuration
public class NacosConfig {

    private final Logger logger = LoggerFactory.getLogger(NacosConfig.class);

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<List<GatewayFlowRuleEntity>, String> gatewayFlowRuleEntityEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<List<ApiDefinitionEntity>, String> apiDefinitionEntityEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
        return s -> JSON.parseArray(s, DegradeRuleEntity.class);
    }

    @Bean
    public Converter<String, List<ParamFlowRuleEntity>> paramFlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, ParamFlowRuleEntity.class);
    }

    @Bean
    public Converter<String,List<SystemRuleEntity>> systemRuleEntityDecoder() {
        return s -> JSON.parseArray(s, SystemRuleEntity.class);
    }

    @Bean
    public Converter<String,List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
        return s -> JSON.parseArray(s, AuthorityRuleEntity.class);
    }

    @Bean
    public Converter<String,List<GatewayFlowRuleEntity>> gatewayFlowRuleEntityDecoder(){
        return s -> JSON.parseArray(s, GatewayFlowRuleEntity.class);
    }

    @Bean
    public Converter<String,List<ApiDefinitionEntity>> apiDefinitionEntityDecoder(){
        return s -> JSON.parseArray(s, ApiDefinitionEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception {
        String namespace = System.getProperty("nacos.namespace");
        String serverAddr = Optional.ofNullable(System.getProperty("nacos.serverAddr")).orElse("localhost:8848");

        logger.info("nacos.namespace={}", namespace);
        logger.info("nacos.serverAddr={}", serverAddr);

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        if(StringUtil.isNotBlank(namespace)){
            properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
        }
        return ConfigFactory.createConfigService(properties);

    }
}
