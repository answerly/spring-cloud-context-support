package com.github.spring.cloud.context.support.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;

/**
 * ZoneAvoidanceRule and ServerTag
 *
 * @author liuyan
 * @date 2018-10-15
 * @see ZoneAvoidanceRule
 */
public class ServerTagAvoidanceRule extends PredicateBasedRule implements IRule {

    private ZoneAvoidanceRule zoneAvoidanceRule;

    private CompositePredicate compositePredicate;

    private CompositePredicate fallbackCompositePredicate;

    public ServerTagAvoidanceRule() {
        this.initWithNiwsConfig(null);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        ServerMetadataPredicate metadataPredicate = new ServerMetadataPredicate(this);

        ServerTagPredicate serviceTagPredicate = new ServerTagPredicate(this);

        DefaultServerTagPredicate defaultServerTagPredicate = new DefaultServerTagPredicate(this);

        ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
        if (clientConfig != null) {
            zoneAvoidanceRule.initWithNiwsConfig(clientConfig);
        }
        AbstractServerPredicate zoneAvoidancePredicate = zoneAvoidanceRule.getPredicate();

        this.fallbackCompositePredicate = CompositePredicate
                .withPredicates(metadataPredicate, defaultServerTagPredicate, zoneAvoidancePredicate)
                .build();

        this.compositePredicate = CompositePredicate
                .withPredicates(metadataPredicate, serviceTagPredicate, zoneAvoidancePredicate)
                .addFallbackPredicate(fallbackCompositePredicate)
                .build();

        this.zoneAvoidanceRule = zoneAvoidanceRule;
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return compositePredicate;
    }
}
