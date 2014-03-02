package com.shopzilla.service.productsearch.resource;

import com.shopzilla.site.service.productsearch.model.jaxb.CommentEntry;

import java.util.Comparator;

/**
 * Created by brett on 3/1/14.
 */
public class CommentEntryComparator implements Comparator<CommentEntry> {

    @Override
    public int compare(CommentEntry o1, CommentEntry o2) {
        return o2.getRating().compareTo(o1.getRating());
    }
}
