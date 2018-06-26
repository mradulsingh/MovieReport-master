package com.android.example.github.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieResponse implements Parcelable{

    private MovieInfoo[] results;

    private String page;

    private String total_pages;

    private String total_results;

    protected MovieResponse(Parcel in) {
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public MovieInfoo[] getResults() {
        return results;
    }

    public void setResults(MovieInfoo[] results) {
        this.results = results;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "ClassPojo [results = " + results + ", page = " + page + ", total_pages = " + total_pages + ", total_results = " + total_results + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(page);
        parcel.writeString(total_pages);
        parcel.writeString(total_results);
    }
}


