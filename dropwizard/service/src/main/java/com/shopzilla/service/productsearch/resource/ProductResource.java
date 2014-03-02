package com.shopzilla.service.productsearch.resource;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import com.shopzilla.service.productsearch.Format;
import com.shopzilla.service.productsearch.data.SolrDao;
import com.shopzilla.service.productsearch.data.SolrProductEntry;
import com.shopzilla.site.service.productsearch.model.jaxb.CommentEntry;
import com.shopzilla.site.service.productsearch.model.jaxb.ProductEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * @author Brett Konold
 */
@Path("/product")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProductResource {

    private SolrDao solrDao;

    public ProductResource(SolrDao dao) {
        this.solrDao = dao;
    }

    @GET
    @JSONP
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{pid}")
    public Response get(@PathParam("pid") String pid,
                        @QueryParam("format") Format format) throws Exception {
        if (pid == null || pid.equals(""))
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();

        SolrProductEntry solrProductEntry = solrDao.getProduct(pid);
        if (solrProductEntry == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        ProductEntry productEntry = new ProductEntry();
        // TODO: make sure all PIDs are valid longs?
        productEntry.setPid(Long.parseLong(solrProductEntry.getPid()));
        productEntry.setBrand(solrProductEntry.getBrand());
        productEntry.setName(solrProductEntry.getDisplayName());
        productEntry.setTitle(solrProductEntry.getTitle());

        for (String category : solrProductEntry.getCategory()) {
            productEntry.getCategories().add(category);
        }

        int length = solrProductEntry.getReviewRatings().size();

        for (int i = 0; i < length; i++) {
            CommentEntry commentEntry = new CommentEntry();
            commentEntry.setContent(solrProductEntry.getReviewContents().get(i));
            commentEntry.setTitle(solrProductEntry.getReviewTitles().get(i));
            String strRating = solrProductEntry.getReviewRatings().get(i);
            double rating = (strRating == null || strRating.equals("")) ? 0 : Double.parseDouble(strRating);
            commentEntry.setRating(rating);

            productEntry.getCommentEntry().add(commentEntry);
        }
        Collections.sort(productEntry.getCommentEntry(), new CommentEntryComparator());

        return buildResponse(productEntry, format);
    }
    private Response buildResponse(Object response, Format format) {
        return Response.ok(response)
                .type(format != null ? format.getMediaType() : Format.xml.getMediaType())
                .build();
    }

    public SolrDao getDao() {
        return solrDao;
    }

    public void setDao(SolrDao dao) {
        this.solrDao = dao;
    }
}
