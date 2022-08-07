package kynv1.fsoft.appmoviefinally.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String original_title;
    private String release_date;
    private String vote_average;
    private String overview;
    private int backdrop_path;


    public Movie(String original_title, String release_date, String vote_average, String overview, int backdrop_path) {
        this.original_title = original_title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(int backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    @Override
    public String toString() {
        return "Popular{" +
                "release_date='" + release_date + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", overview='" + overview + '\'' +
                ", backdrop_path=" + backdrop_path +
                '}';
    }


}
