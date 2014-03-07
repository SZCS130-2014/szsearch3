package com.shopzilla.service.productsearch.data;

import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

/**
 * Created by brett on 2/23/14.
 */
public class SolrProductEntry {
    //TODO: maybe come up with a better naming scheme for the variables

    private String pid;
    private String category;
    private String displayName;
    private String brand;
    private String title;
    private Double rating;
    private String imgUrl;
    private List<String> reviewRatings;
    private List<String> reviewTitles;
    private List<String> reviewContents;

    public String getPid() {
        return pid;
    }

    @Field("PID")
    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    @Field("Category")
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Field("DisplayName")
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public String getBrand() {
        return brand;
    }

    @Field("Brand")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    @Field("ProductTitle")
    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    @Field("AvgRating")
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<String> getReviewRatings() {
        return reviewRatings;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Field("ImgUrl")
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Field("Rating")
    public void setReviewRatings(List<String> reviewRatings) {
        this.reviewRatings = reviewRatings;
    }

    public List<String> getReviewTitles() {
        return reviewTitles;
    }

    @Field("ReviewTitle")
    public void setReviewTitles(List<String> reviewTitles) {
        this.reviewTitles = reviewTitles;
    }

    public List<String> getReviewContents() {
        return reviewContents;
    }

    @Field("Review")
    public void setReviewContents(List<String> reviewContents) {
        this.reviewContents = reviewContents;
    }

}
