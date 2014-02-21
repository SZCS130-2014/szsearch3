/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopzilla.service.productsearch.config.CustomHttpConfiguration;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Shopping cart service configuration.
 */
public class ProductSearchServiceConfiguration extends Configuration {

    @JsonProperty("http")
    private CustomHttpConfiguration httpConfiguration = new CustomHttpConfiguration();
    
    public CustomHttpConfiguration getHttpConfiguration() {
        return httpConfiguration;
    }

    public void setHttpConfiguration(CustomHttpConfiguration httpConfiguration) {
        this.httpConfiguration = httpConfiguration;
    }
}
