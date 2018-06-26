package com.android.example.github.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

//@Entity(tableName = "MovieInfoo")
public class MovieInfoo implements Parcelable{
    private String vote_average;

    @Ignore
    private String backdrop_path;
    @Ignore
    private String adult;

    private String id;
    @Ignore
    private String title;
    @ColumnInfo(name = "description")
    private String overview;
    @ColumnInfo(name = "language")
    private String original_language;
    @Ignore
    private String[] genre_ids;

    private String release_date;
    @ColumnInfo(name = "title")
    private String original_title;
    @Ignore
    private String vote_count;
    @Ignore
    private String poster_path;
    @Ignore
    private String video;
    @Ignore
    private String popularity;
    @Ignore
    public MovieInfoo() {
    }

    public MovieInfoo(Parcel in) {
        vote_average = in.readString();
        backdrop_path = in.readString();
        adult = in.readString();
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        original_language = in.readString();
        genre_ids = in.createStringArray();
        release_date = in.readString();
        original_title = in.readString();
        vote_count = in.readString();
        poster_path = in.readString();
        video = in.readString();
        popularity = in.readString();
    }

    public static final Creator<MovieInfoo> CREATOR = new Creator<MovieInfoo>() {
        @Override
        public MovieInfoo createFromParcel(Parcel in) {
            return new MovieInfoo(in);
        }

        @Override
        public MovieInfoo[] newArray(int size) {
            return new MovieInfoo[size];
        }
    };

    public String getVote_average ()
    {
        return vote_average;
    }

    public void setVote_average (String vote_average)
    {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path ()
    {
        return backdrop_path;
    }

    public void setBackdrop_path (String backdrop_path)
    {
        this.backdrop_path = backdrop_path;
    }

    public String getAdult ()
    {
        return adult;
    }

    public void setAdult (String adult)
    {
        this.adult = adult;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getOverview ()
    {
        return overview;
    }

    public void setOverview (String overview)
    {
        this.overview = overview;
    }

    public String getOriginal_language ()
    {
        return original_language;
    }

    public void setOriginal_language (String original_language)
    {
        this.original_language = original_language;
    }

    public String[] getGenre_ids ()
    {
        return genre_ids;
    }

    public void setGenre_ids (String[] genre_ids)
    {
        this.genre_ids = genre_ids;
    }

    public String getRelease_date ()
    {
        return release_date;
    }

    public void setRelease_date (String release_date)
    {
        this.release_date = release_date;
    }

    public String getOriginal_title ()
    {
        return original_title;
    }

    public void setOriginal_title (String original_title)
    {
        this.original_title = original_title;
    }

    public String getVote_count ()
    {
        return vote_count;
    }

    public void setVote_count (String vote_count)
    {
        this.vote_count = vote_count;
    }

    public String getPoster_path ()
    {
        return poster_path;
    }

    public void setPoster_path (String poster_path)
    {
        this.poster_path = poster_path;
    }

    public String getVideo ()
    {
        return video;
    }

    public void setVideo (String video)
    {
        this.video = video;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [vote_average = "+vote_average+", backdrop_path = "+backdrop_path+", adult = "+adult+", id = "+id+", title = "+title+", overview = "+overview+", original_language = "+original_language+", genre_ids = "+genre_ids+", release_date = "+release_date+", original_title = "+original_title+", vote_count = "+vote_count+", poster_path = "+poster_path+", video = "+video+", popularity = "+popularity+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(vote_average);
        parcel.writeString(backdrop_path);
        parcel.writeString(adult);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(original_language);
        parcel.writeStringArray(genre_ids);
        parcel.writeString(release_date);
        parcel.writeString(original_title);
        parcel.writeString(vote_count);
        parcel.writeString(poster_path);
        parcel.writeString(video);
        parcel.writeString(popularity);
    }
}
