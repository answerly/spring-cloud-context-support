package com.olasharing.commons.context.ribbon.rule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务标签协议自动配置
 *
 * @author liuyan
 * @date 2018-10-15
 */
@Configuration
public class ServerTagRuleAutoConfiguration {

    @Bean
    public IRule iRule() {
        return new ServerTagAvoidanceRule();
    }
}
