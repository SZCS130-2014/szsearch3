/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch.resource;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import com.shopzilla.service.productsearch.Format;
import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.site.service.productsearch.model.jaxb.ProductSearchResponse;
import com.shopzilla.site.service.productsearch.model.jaxb.ProductEntry;
import com.shopzilla.site.service.productsearch.model.jaxb.CommentEntry;
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
 * TODO: a lot
 * @author Brett Konold
 */
@Path("/productsearch")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProductSearchResource {

    private SolrDao dao;

    public ProductSearchResource(SolrDao dao) {
        this.dao = dao;
    }

    @GET
    @JSONP
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //@Path("")
    public Response get(@QueryParam("q") String query,
                        @QueryParam("format") Format format) {
        if (query == null || query.length() < 1) {
            // TODO: log?
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        ProductSearchResponse response = new ProductSearchResponse();
        // TODO: map from SolrProductEntry to ProductEntry
        return buildResponse(response, format);
    }

    private Response buildResponse(Object response, Format format) {
        return Response.ok(response)
                .type(format != null ? format.getMediaType() : Format.xml.getMediaType())
                .build();
    }

    public SolrDao getDao() {
        return dao;
    }

    public void setDao(SolrDao dao) {
        this.dao = dao;
    }

}
