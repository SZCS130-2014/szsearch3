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
        List<SolrProductEntry> products = dao.getSearchResults("apple");
        for (SolrProductEntry p : products) {
            System.out.println("PID: " + p.getPid() + " DisplayName: " + p.getDisplayName());
        }
    }

    private SolrServer solrServer;
    private String requestHandler;

    public SolrDao(String solrBaseUrl, String requestHandler) {
        this.solrServer = new HttpSolrServer(solrBaseUrl);
        this.requestHandler = requestHandler;
    }

    public SolrServer getSolrServer() {
        return solrServer;
    }

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public List<SolrProductEntry> getSearchResults(String query) throws Exception {
        if (query == null || query.length() < 1)
            return new LinkedList<SolrProductEntry>(); // TODO: throw exception instead?

        SolrQuery solrQuery = new SolrQuery().setQuery(query).setRequestHandler(requestHandler);
        QueryResponse response = solrServer.query(solrQuery); // TODO: exception handling?
        return response.getBeans(SolrProductEntry.class);
    }
}
