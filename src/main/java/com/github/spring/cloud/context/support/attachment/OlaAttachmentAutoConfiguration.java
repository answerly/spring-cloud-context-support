package com.github.spring.cloud.context.support.attachment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 附件协议自动配置
 *
 * @author liuyan
 * @date 2018-10-15
 */
@Configuration
public class OlaAttachmentAutoConfiguration {

    @Bean
    OlaAttachmentRequestInterceptor olaAttachmentRequestInterceptor() {
        return new OlaAttachmentRequestInterceptor();
    }

    @Bean
    OlaAttachment2ContextAspect olaAttachment2ContextAspect() {
        return new OlaAttachment2ContextAspect();
    }
}
