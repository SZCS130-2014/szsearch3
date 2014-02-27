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

    public static void main(String[] args) throws Exception {
        SolrDao dao = new SolrDao("http://localhost:8983/solr/collection1", "/simpleweighted");
        List<SolrProductEntry> products = dao.getSearchResults("apple", null, null);
        for (SolrProductEntry p : products) {
            System.out.println("PID: " + p.getPid() + " DisplayName: " + p.getDisplayName());
        }
    }

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
