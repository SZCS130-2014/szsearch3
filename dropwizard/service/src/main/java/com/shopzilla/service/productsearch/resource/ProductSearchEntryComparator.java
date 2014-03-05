package com.shopzilla.service.productsearch.resource;

import com.shopzilla.site.service.productsearch.model.jaxb.ProductSearchEntry;

import java.util.Comparator;

/**
 * Created by brett on 3/4/14.
 */
public class ProductSearchEntryComparator implements Comparator<ProductSearchEntry> {
    @Override
    public int compare(ProductSearchEntry o1, ProductSearchEntry o2) {
        return o2.getRating().compareTo(o1.getRating());
    }
}
