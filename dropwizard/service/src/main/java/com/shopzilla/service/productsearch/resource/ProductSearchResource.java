/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch.resource;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import com.shopzilla.service.productsearch.Format;
import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.service.productsearch.data.SolrProductEntry;
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
 * @author Brett Konold
 */
@Path("/productsearch")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProductSearchResource {

    private SolrDao solrDao;

    public ProductSearchResource(SolrDao dao) {
        this.solrDao = dao;
    }

    @GET
    @JSONP
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //@Path("")
    public Response get(@QueryParam("q") String query,
                        @QueryParam("format") Format format) throws Exception {
        if (query == null || query.length() < 1) {
            // TODO: log?
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        ProductSearchResponse response = new ProductSearchResponse();

        List<SolrProductEntry> solrProductEntries = solrDao.getSearchResults(query);
        for (SolrProductEntry solrProductEntry : solrProductEntries) {
            ProductEntry productEntry = new ProductEntry();
            // TODO: make sure all PIDs are valid longs?
            productEntry.setPid(Long.parseLong(solrProductEntry.getPid()));
            productEntry.setBrand(solrProductEntry.getBrand());
            productEntry.setCategory(solrProductEntry.getCategory());
            productEntry.setName(solrProductEntry.getDisplayName());
            productEntry.setTitle(solrProductEntry.getTitle());

            int length = solrProductEntry.getReviewRatings().size();

            for (int i = 0; i < length; i++) {
                CommentEntry commentEntry = new CommentEntry();
                commentEntry.setContent(solrProductEntry.getReviewContents().get(i));
                commentEntry.setTitle(solrProductEntry.getReviewTitles().get(i));
                String strRating = solrProductEntry.getReviewRatings().get(i);
                double rating = (strRating == null || strRating.equals("")) ? 0 : Double.parseDouble(strRating);
                commentEntry.setRating(rating);

                productEntry.getCommentEntry().add(commentEntry);
            }
            response.getProductEntry().add(productEntry);
        }
        return buildResponse(response, format);
    }

    private Response buildResponse(Object response, Format format) {
        return Response.ok(response)
                .type(format != null ? format.getMediaType() : Format.xml.getMediaType())
                .build();
    }

    public SolrDao getDao() {
        return solrDao;
    }

    public void setDao(SolrDao dao) {
        this.solrDao = dao;
    }

}
