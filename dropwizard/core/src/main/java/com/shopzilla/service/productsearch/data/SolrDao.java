package com.shopzilla.service.productsearch.data;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

/**
 * Created by brett on 2/23/14.
 * TODO: issue query to solr, map solr response into list of SolrProductEntry
 */
public class SolrDao {

    public static void main(String[] args) throws Exception {
        SolrDao dao = new SolrDao("http://localhost:8983/solr/collection1");
        List<SolrProductEntry> products = dao.getSearchResults("apple");
        for (SolrProductEntry p : products) {
            System.out.println(p.getDisplayName());
        }
    }

    private SolrServer solrServer;
    private static final String requestHandler = "/simpleweighted";

    public SolrDao(String solrBaseUrl) {
        this.solrServer = new HttpSolrServer(solrBaseUrl);
    }

    public SolrServer getSolrServer() {
        return solrServer;
    }

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public List<SolrProductEntry> getSearchResults(String query) throws Exception {
        if (query == null || query.length() < 1)
            return null; // TODO: throw exception?

        SolrQuery solrQuery = new SolrQuery().setQuery(query).setRequestHandler(requestHandler);
        QueryResponse response = solrServer.query(solrQuery);
        return response.getBeans(SolrProductEntry.class);
    }
}
