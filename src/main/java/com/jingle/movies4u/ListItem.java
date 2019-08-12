package com.jingle.movies4u;

public class ListItem {

    private String title;
    private String desc;
    private String imageUrl;
    private double rating;
    private int releaseYear;

    public ListItem(String title, String image, double rating, int releaseYear, String desc) {
        this.title = title;
        this.imageUrl = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}
