package com.shopzilla.service.productsearch.data;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brett on 2/23/14.
 */
public class SolrDao {

    private String solrBaseUrl;
    private String requestHandler;

    public SolrDao(String solrBaseUrl, String requestHandler) {
        this.solrBaseUrl = solrBaseUrl;
        this.requestHandler = requestHandler;
    }

    public String getSolrBaseUrl() {
        return this.solrBaseUrl;
    }

    public void setSolrBaseUrl(String solrBaseUrl) {
        this.solrBaseUrl = solrBaseUrl;
    }

    /**
    * @param pid the id of the product of interest
    * @return the product with the given PID, null otherwise
     */
    public SolrProductEntry getProduct(String pid) throws Exception {
        if (pid == null)
            return null; //TODO: not return null? not sure about this

        String query = String.format("PID:%s", pid);

        SolrServer solrServer = new HttpSolrServer(solrBaseUrl);
        SolrQuery solrQuery = new SolrQuery().setQuery(query);

        // do not set the request handler -- default "/select" will be used
        QueryResponse response = solrServer.query(solrQuery);
        solrServer.shutdown();
        List<SolrProductEntry> solrProductEntries = response.getBeans(SolrProductEntry.class);
        return solrProductEntries.size() == 0 ? null : solrProductEntries.get(0);
    }

    /**
     * @param query plaintext query issued by the user
     * @param start number of results from top to skip
     * @param rows number of results to return
     * @return list of all relevant products to the query
     * @throws Exception
     */
    public List<SolrProductEntry> getSearchResults(String query, Integer start, Integer rows) throws Exception {
        if (query == null || query.length() < 1)
            return new LinkedList<SolrProductEntry>();

        SolrServer solrServer = new HttpSolrServer(solrBaseUrl);
        SolrQuery solrQuery = new SolrQuery().setQuery(query)
                                             .setRequestHandler(requestHandler);
        if (start != null && rows != null) {
            solrQuery.setRows(rows);
            solrQuery.setStart(start);
        }
        
        QueryResponse response = solrServer.query(solrQuery); // TODO: exception handling?
        solrServer.shutdown();
        return response.getBeans(SolrProductEntry.class);
    }
}
