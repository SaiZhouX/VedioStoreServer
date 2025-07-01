package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Movie {
    @JsonProperty("title")
    private String title;
    @JsonProperty("poster_path")
    private String posterPath;
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
}