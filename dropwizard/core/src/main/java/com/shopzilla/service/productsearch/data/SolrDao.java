package com.shopzilla.service.productsearch.data;

/**
 * Created by brett on 2/23/14.
 * TODO: issue query to solr, map solr response into list of SolrProductEntry
 */
public class SolrDao {

    private String solrBaseUrl;

    public SolrDao(String solrBaseUrl) {
        this.solrBaseUrl = solrBaseUrl;
    }

    public String getSolrBaseUrl() {
        return solrBaseUrl;
    }

    public void setSolrBaseUrl(String solrBaseUrl) {
        this.solrBaseUrl = solrBaseUrl;
    }

}
