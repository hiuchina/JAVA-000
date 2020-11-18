package com.homework.prop;

/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 *
 * @since 2020/11/18 11:09 下午
 */
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Spring boot properties configuration.
 */
@ConfigurationProperties(prefix = "homework")
@Getter
@Setter
public final class SpringBootPropertiesConfiguration {

    private Properties props = new Properties();
}
