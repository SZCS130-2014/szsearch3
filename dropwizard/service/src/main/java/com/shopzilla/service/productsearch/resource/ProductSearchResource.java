/**
 * Copyright (C) 2004 - 2013 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */
package com.shopzilla.service.productsearch.resource;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import com.shopzilla.service.productsearch.Format;
import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.service.productsearch.data.SolrProductEntry;
import com.shopzilla.service.productsearch.data.SolrSearchResponse;
import com.shopzilla.site.service.productsearch.model.jaxb.Facet;
import com.shopzilla.site.service.productsearch.model.jaxb.FacetAttribute;
import com.shopzilla.site.service.productsearch.model.jaxb.ProductSearchEntry;
import com.shopzilla.site.service.productsearch.model.jaxb.ProductSearchResponse;
import org.apache.solr.client.solrj.response.FacetField;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    // TODO: filter out unnecessary product fields (e.g don't need reviews to render the results page) at java or solr layer?
    public Response get(@QueryParam("q") String query,
                        @QueryParam("format") Format format,
                        @QueryParam("start") Integer start,
                        @QueryParam("rows") Integer rows,
                        @QueryParam("categoryFilter") String categoryFilter,
                        @QueryParam("ratingFilter") Integer ratingFilter,
                        @QueryParam("sort") Boolean sort) throws Exception {
        if (query == null || query.length() < 1) {
            // TODO: log?
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        ProductSearchResponse response = new ProductSearchResponse();
        SolrSearchResponse solrSearchResponse = solrDao.getSearchResults(query, start, rows, categoryFilter, ratingFilter);

        // add all solrProductEntries to response
        List<SolrProductEntry> solrProductEntries = solrSearchResponse.getSolrProductEntries();
        for (SolrProductEntry solrProductEntry : solrProductEntries) {
            ProductSearchEntry productSearchEntry = new ProductSearchEntry();
            productSearchEntry.setPid(Long.parseLong(solrProductEntry.getPid()));
            productSearchEntry.setBrand(solrProductEntry.getBrand());
            String displayName = solrProductEntry.getDisplayName();
            productSearchEntry.setName(displayName == null ? "" : displayName);
            productSearchEntry.setTitle(solrProductEntry.getTitle());
            productSearchEntry.setRating(solrProductEntry.getRating());

            if (solrProductEntry.getCategory() != null) {
                for (String category : solrProductEntry.getCategory()) {
                    productSearchEntry.getCategories().add(category);
                }
            }
            response.getProductSearchEntry().add(productSearchEntry);
        }

        for (FacetField facetField : solrSearchResponse.getFacetFields()) {
            FacetAttribute facetAttribute = new FacetAttribute();
            facetAttribute.setName(facetField.getName());
            for (FacetField.Count c : facetField.getValues()) {
                Facet facet = new Facet();
                facet.setName(c.getName());
                facet.setNumFound(c.getCount());
                facetAttribute.getFacet().add(facet);
            }
            response.getFacetAttribute().add(facetAttribute);
        }

        addRatingFacets(response, solrSearchResponse.getFacetQuery());

        // sort
        if (sort != null && sort) {
            Collections.sort(response.getProductSearchEntry(), new ProductSearchEntryComparator());
        }

        response.setNumFound(solrSearchResponse.getNumFound());

        return buildResponse(response, format);
    }

    private Response buildResponse(Object response, Format format) {
        return Response.ok(response)
                .type(format != null ? format.getMediaType() : Format.json.getMediaType())
                .build();
    }

    private void addRatingFacets(ProductSearchResponse response, Map<String, Integer> facetQuery) {
        FacetAttribute facetAttribute = new FacetAttribute();
        facetAttribute.setName("Rating");

        Facet facet = new Facet();
        facet.setName("1");
        facet.setNumFound(facetQuery.get("AvgRating: [1 TO *]").longValue());
        facetAttribute.getFacet().add(facet);

        facet = new Facet();
        facet.setName("2");
        facet.setNumFound(facetQuery.get("AvgRating: [2 TO *]").longValue());
        facetAttribute.getFacet().add(facet);

        facet = new Facet();
        facet.setName("3");
        facet.setNumFound(facetQuery.get("AvgRating: [3 TO *]").longValue());
        facetAttribute.getFacet().add(facet);

        facet = new Facet();
        facet.setName("4");
        facet.setNumFound(facetQuery.get("AvgRating: [4 TO *]").longValue());
        facetAttribute.getFacet().add(facet);

        facet = new Facet();
        facet.setName("5");
        facet.setNumFound(facetQuery.get("AvgRating: [5 TO *]").longValue());
        facetAttribute.getFacet().add(facet);

        response.getFacetAttribute().add(facetAttribute);
    }

    public SolrDao getDao() {
        return solrDao;
    }

    public void setDao(SolrDao dao) {
        this.solrDao = dao;
    }

}
