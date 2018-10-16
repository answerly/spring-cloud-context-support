package com.github.spring.cloud.context.support.ribbon.rule;

import com.google.common.base.Predicate;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.github.spring.cloud.context.support.attachment.OlaAttachmentContext;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 服务标签验证
 * 有限选择相同服务标签
 *
 * @author liuyan
 * @date 2018-10-15
 */
public class ServerTagPredicate extends AbstractServerPredicate implements Predicate<PredicateKey> {

    ServerTagPredicate(IRule rule) {
        super(rule);
    }

    @Override
    public boolean apply(PredicateKey predicateKey) {
        String consumerTag = getConsumerTag();
        String producerTag = getProducerTag(predicateKey);
        return consumerTag != null && consumerTag.equalsIgnoreCase(producerTag);
    }


    private String getConsumerTag() {
        String consumerServerTag = OlaAttachmentContext.getAttachment(Constants.ATTACHMENT_KEY_SERVER_TAG);
        if (StringUtils.isNotBlank(consumerServerTag)) {
            return consumerServerTag;
        }
        return Constants.SERVER_TAG_DEFAULT;
    }

    private String getProducerTag(PredicateKey predicateKey) {
        String serverTag = null;
        DiscoveryEnabledServer server = getDiscoveryEnabledServer(predicateKey);
        Map<String, String> metadata = server.getInstanceInfo().getMetadata();
        if (metadata != null) {
            serverTag = metadata.get(Constants.EUREKA_METADATA_SERVER_TAG);
        }
        if (serverTag == null) {
            serverTag = Constants.SERVER_TAG_DEFAULT;
        }
        return serverTag;
    }

    private DiscoveryEnabledServer getDiscoveryEnabledServer(PredicateKey predicateKey) {
        return (DiscoveryEnabledServer) predicateKey.getServer();
    }
}
