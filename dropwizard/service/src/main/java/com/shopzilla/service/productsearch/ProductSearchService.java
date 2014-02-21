/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch;

import com.google.common.collect.Lists;
import com.shopzilla.service.productsearch.resource.*;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import com.yammer.dropwizard.views.ViewBundle;
import com.yammer.metrics.util.DeadlockHealthCheck;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 * Main entry point for product search service.
 */
public class ProductSearchService extends Service<ProductSearchServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new ProductSearchService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ProductSearchServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    @Override
    public void run(ProductSearchServiceConfiguration configuration, Environment environment)
            throws Exception {
        // environment.add(new ProductSearchResource());
        // environment.add(new VersionResource());
        // environment.add(new ConfigurationResource(configuration));
    }

}
