package kynv1.fsoft.appmoviefinally.model;

import androidx.lifecycle.LiveData;

import java.io.Serializable;
import java.util.List;

import kynv1.fsoft.appmoviefinally.model.Result;

public class MovieItem implements Serializable {
    private Integer page;
    private List<Result> results;
    private Integer total_pages;
    private Integer total_results;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }
}
