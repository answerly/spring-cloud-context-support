package com.olasharing.commons.context.trace;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http分布式跟踪自动加载
 *
 * @author liuyan
 * @date 2018-10-16
 */
@Configuration
public class HttpTraceAutoConfiguration {

    @Bean
    ResponseTraceIdFilter responseTraceIdFilter(@Autowired Tracer tracer) {
        return new ResponseTraceIdFilter(tracer);
    }
}
