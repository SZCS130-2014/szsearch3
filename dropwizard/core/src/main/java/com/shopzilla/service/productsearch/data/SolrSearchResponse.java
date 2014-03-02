package com.shopzilla.service.productsearch.data;

import org.apache.solr.client.solrj.response.FacetField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brett on 3/1/14.
 * Wrapper for what is returned by Solr
 */
public class SolrSearchResponse {

    private List<SolrProductEntry> solrProductEntries;
    private Long numFound;
    private List<FacetField> facetFields;

    public SolrSearchResponse() {
        this.solrProductEntries = new ArrayList<SolrProductEntry>();
        this.numFound = 0L;
        this.facetFields = new ArrayList<FacetField>();
    }

    public SolrSearchResponse(List<SolrProductEntry> solrProductEntries,
                              Long numFound,
                              List<FacetField> facetFields) {
        this.solrProductEntries = solrProductEntries;
        this.numFound = numFound;
        this.facetFields = facetFields;
    }

    public List<SolrProductEntry> getSolrProductEntries() {
        return solrProductEntries;
    }

    public void setSolrProductEntries(List<SolrProductEntry> solrProductEntries) {
        this.solrProductEntries = solrProductEntries;
    }

    public Long getNumFound() {
        return numFound;
    }

    public void setNumFound(Long numFound) {
        this.numFound = numFound;
    }

    public List<FacetField> getFacetFields() {
        return facetFields;
    }

    public void setFacetFields(List<FacetField> facetFields) {
        this.facetFields = facetFields;
    }
}
