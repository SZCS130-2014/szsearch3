package com.shopzilla.service.productsearch;

import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.service.productsearch.data.SolrProductEntry;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Created by brett on 2/24/14.
 */
public class TestSolrDao {

    private static final String solrBaseUrl = "http://localhost:8983/solr/collection1";

    @Test
    public void testGetSearchResults() throws Exception {
        SolrDao solrDao = new SolrDao(solrBaseUrl);
        String testQuery = "apple";
        List<SolrProductEntry> productEntries = solrDao.getSearchResults(testQuery);
        assertNotNull(productEntries);
    }

}
