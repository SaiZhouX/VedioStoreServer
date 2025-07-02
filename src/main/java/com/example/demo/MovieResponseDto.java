package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieResponseDto {
    @JsonProperty("title")
    private String title;

    @JsonProperty("full_poster_path")
    private String fullPosterPath;

    @JsonProperty("stars")
    private String stars;

    @JsonProperty("director")
    private String director;

    @JsonProperty("type")
    private String type;

    @JsonProperty("region")
    private String region;

    @JsonProperty("level")
    private String level;

    @JsonProperty("download_link")
    private String downloadLink;

    @JsonProperty("watch_link")
    private String watchLink;

    @JsonProperty("synopsis")
    private String synopsis;

    // 构造函数：从Movie对象转换
    public MovieResponseDto(Movie movie) {
        this.title = movie.getTitle();
        this.fullPosterPath = movie.getFullPosterPath();
        this.stars = movie.getStars();
        this.director = movie.getDirector();
        this.type = movie.getType();
        this.region = movie.getRegion();
        this.level = movie.getLevel();
        this.downloadLink = movie.getDownloadLink();
        this.watchLink = movie.getWatchLink();
        this.synopsis = movie.getSynopsis();
    }
}