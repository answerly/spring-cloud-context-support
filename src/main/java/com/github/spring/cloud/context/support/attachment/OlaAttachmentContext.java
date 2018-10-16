package com.github.spring.cloud.context.support.attachment;

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;

/**
 * 服务标签上下文
 *
 * @author liuyan
 * @date 2018-10-15
 */
public class OlaAttachmentContext {

    private static final ThreadLocal<OlaAttachmentContext> INNER_ATTACHMENT_CONTEXT = new ThreadLocal<OlaAttachmentContext>() {
        @Override
        protected OlaAttachmentContext initialValue() {
            return new OlaAttachmentContext();
        }
    };

    private Map<String, String> attachments = Maps.newHashMapWithExpectedSize(4);

    private OlaAttachmentContext() {
    }

    public static void clear() {
        INNER_ATTACHMENT_CONTEXT.remove();
    }

    /**
     * 添加附件到上下文
     * attachmentKey必须以"X-OLA-ATT-"开头
     *
     * @param attachmentKey
     * @param attachmentValue
     */
    public static void addAttachment(String attachmentKey, String attachmentValue) {
        if (attachmentKey.startsWith(Constants.ATTACHMENT_KEY_PREFIX)) {
            OlaAttachmentContext olaAttachmentContext = INNER_ATTACHMENT_CONTEXT.get();
            olaAttachmentContext.attachments.put(attachmentKey, attachmentValue);
        }
    }

    /**
     * 获取附件内容
     *
     * @param attachmentKey
     * @return
     */
    public static String getAttachment(String attachmentKey) {
        OlaAttachmentContext olaAttachmentContext = INNER_ATTACHMENT_CONTEXT.get();
        return olaAttachmentContext.attachments.get(attachmentKey);
    }

    /**
     * 获取所有附件协议Key
     *
     * @return
     */
    public static Iterator<String> attachmentKeys() {
        OlaAttachmentContext olaAttachmentContext = INNER_ATTACHMENT_CONTEXT.get();
        return olaAttachmentContext.attachments.keySet().iterator();
    }
}
