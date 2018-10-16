package com.github.spring.cloud.context.support.ribbon.rule;

import com.google.common.base.Predicate;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * 服务metadata断言
 *
 * @author liuyan
 * @date 2018-10-15
 */
public class ServerMetadataPredicate extends AbstractServerPredicate implements Predicate<PredicateKey> {

    ServerMetadataPredicate(IRule rule) {
        super(rule);
    }

    @Override
    public boolean apply(PredicateKey predicateKey) {
        Server server = predicateKey.getServer();
        return server != null && server instanceof DiscoveryEnabledServer;
    }
}
