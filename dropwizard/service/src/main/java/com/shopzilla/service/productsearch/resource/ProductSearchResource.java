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
    // TODO: filter out unnecessary product fields (e.g don't need reviews to render the results page) at java or solr layer?
    public Response get(@QueryParam("q") String query,
                        @QueryParam("format") Format format,
                        @QueryParam("start") Integer start,
                        @QueryParam("rows") Integer rows,
                        @QueryParam("filterField") String filterField,
                        @QueryParam("filterValue") String filterValue) throws Exception {
        if (query == null || query.length() < 1 || !filterField.equals("Category")) {
            // TODO: log?
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        ProductSearchResponse response = new ProductSearchResponse();
        boolean isFilteredSearch = filterField != null && filterValue != null;
        SolrSearchResponse searchResults = isFilteredSearch
                                           ? solrDao.getFilteredSearchResults(query, start, rows, filterField, filterValue)
                                           : solrDao.getSearchResults(query, start, rows);

        // add all solrProductEntries to response
        List<SolrProductEntry> solrProductEntries = searchResults.getSolrProductEntries();
        for (SolrProductEntry solrProductEntry : solrProductEntries) {
            ProductSearchEntry productSearchEntry = new ProductSearchEntry();
            productSearchEntry.setPid(Long.parseLong(solrProductEntry.getPid()));
            productSearchEntry.setBrand(solrProductEntry.getBrand());
            String displayName = solrProductEntry.getDisplayName();
            productSearchEntry.setName(displayName == null ? "" : displayName);
            productSearchEntry.setTitle(solrProductEntry.getTitle());

            if (solrProductEntry.getCategory() != null) {
                for (String category : solrProductEntry.getCategory()) {
                    productSearchEntry.getCategories().add(category);
                }
            }
            response.getProductSearchEntry().add(productSearchEntry);
        }

        for (FacetField facetField : searchResults.getFacetFields()) {
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
        response.setNumFound(searchResults.getNumFound());

        return buildResponse(response, format);
    }

    private Response buildResponse(Object response, Format format) {
        return Response.ok(response)
                .type(format != null ? format.getMediaType() : Format.json.getMediaType())
                .build();
    }

    public SolrDao getDao() {
        return solrDao;
    }

    public void setDao(SolrDao dao) {
        this.solrDao = dao;
    }

}
