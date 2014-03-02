package com.shopzilla.service.productsearch;

import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.service.productsearch.data.SolrSearchResponse;

import static org.junit.Assert.*;

/**
 * Created by brett on 2/24/14.
 */
public class TestSolrDao {

    private static final String solrBaseUrl = "http://localhost:8983/solr/collection1";
    private static final String solrRequestHandler = "/simpleweighted";

    //@Test
    // TODO: more thorough test - perhaps actually check that the right results are being returned
    public void testGetSearchResults() throws Exception {
        SolrDao solrDao = new SolrDao(solrBaseUrl, solrRequestHandler);
        String testQuery = "apple";
        SolrSearchResponse solrSearchResponse = solrDao.getSearchResults(testQuery, 0, 1);
        assertNotNull(solrSearchResponse);
        assertEquals(0, solrSearchResponse.getSolrProductEntries().size());
    }

}
