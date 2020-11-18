package com.homework;

import com.homework.prop.SpringBootPropertiesConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/11/18 9:18 下午
 */

@Configuration
@EnableConfigurationProperties(SpringBootPropertiesConfiguration.class)
@ConditionalOnProperty(prefix = "homework", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SpringBootConfiguration implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {

    }
}
