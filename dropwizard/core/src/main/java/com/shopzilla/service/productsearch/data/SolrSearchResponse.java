package com.shopzilla.service.productsearch.data;

import org.apache.solr.client.solrj.response.FacetField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brett on 3/1/14.
 * Wrapper for what is returned by Solr
 */
public class SolrSearchResponse {

    private List<SolrProductEntry> solrProductEntries;
    private Long numFound;
    private List<FacetField> facetFields;
    private Map<String, Integer> facetQuery;

    public SolrSearchResponse() {
        this.solrProductEntries = new ArrayList<SolrProductEntry>();
        this.numFound = 0L;
        this.facetFields = new ArrayList<FacetField>();
        this.facetQuery = new HashMap<String, Integer>();
    }

    public SolrSearchResponse(List<SolrProductEntry> solrProductEntries,
                              Long numFound,
                              List<FacetField> facetFields,
                              Map<String, Integer> facetQueries) {
        this.solrProductEntries = solrProductEntries;
        this.numFound = numFound;
        this.facetFields = facetFields;
        this.facetQuery = facetQueries;
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

    public Map<String, Integer> getFacetQuery() {
        return facetQuery;
    }

    public void setFacetQuery(Map<String, Integer> facetQuery) {
        this.facetQuery = facetQuery;
    }
}
