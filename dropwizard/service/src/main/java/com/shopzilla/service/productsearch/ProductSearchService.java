/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch;

import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.service.productsearch.resource.ConfigurationResource;
import com.shopzilla.service.productsearch.resource.ProductResource;
import com.shopzilla.service.productsearch.resource.ProductSearchResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

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
    }

    @Override
    public void run(ProductSearchServiceConfiguration configuration, Environment environment)
            throws Exception {

        final SolrDao solrDao = new SolrDao(configuration.getSolrBaseUrl(), configuration.getSolrRequestHandler());

        environment.addResource(new ProductSearchResource(solrDao));
        environment.addResource(new ProductResource(solrDao));
        environment.addResource(new ConfigurationResource(configuration));
    }

}
