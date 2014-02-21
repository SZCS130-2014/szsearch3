/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch.resource;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import com.shopzilla.service.productsearch.Format;
import com.shopzilla.service.shoppingcart.data.ShoppingCartDao;
import com.shopzilla.service.shoppingcart.ShoppingCartQuery;
import com.shopzilla.site.service.shoppingcart.model.jaxb.ShoppingCartEntry;
import com.shopzilla.site.service.shoppingcart.model.jaxb.ShoppingCartResponse;
import com.yammer.metrics.annotation.Timed;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controller for handling CRUD operations for a shopping cart.
 * @author Brett Konold
 */
@Path("/productsearch")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
public class ProductSearchResource {

}
