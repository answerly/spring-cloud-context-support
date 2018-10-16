package com.github.spring.cloud.context.support.attachment;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Iterator;

/**
 * 调用方把本地附件传送给提供方
 *
 * @author liuyan
 * @date 2018-10-15
 */
public class OlaAttachmentRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Iterator<String> attachmentKeys = OlaAttachmentContext.attachmentKeys();
        if (attachmentKeys != null) {
            while (attachmentKeys.hasNext()) {
                String attachmentKey = attachmentKeys.next();
                String attachmentValue = OlaAttachmentContext.getAttachment(attachmentKey);
                requestTemplate.header(attachmentKey, attachmentValue);
            }
        }
    }
}
