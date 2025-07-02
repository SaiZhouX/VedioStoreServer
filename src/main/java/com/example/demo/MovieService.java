package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File moviesFile = new File("movies.json");
    private final Object lock = new Object();

    public void saveMovie(Movie movie) throws IOException {
        synchronized (lock) {
            List<Movie> movies = loadMovies();
            movies.add(movie);
            objectMapper.writeValue(moviesFile, movies);
        }
    }

    public List<Movie> loadMovies() throws IOException {
        synchronized (lock) {
            if (!moviesFile.exists() || moviesFile.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(moviesFile,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
        }
    }

    public Optional<Movie> findMovieByTitle(String title) throws IOException {
        return loadMovies().stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst();
    }

    // 更新电影信息的方法
    public boolean updateMovie(Movie updatedMovie) throws IOException {
        synchronized (lock) {
            List<Movie> movies = loadMovies();
            boolean found = false;
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equals(updatedMovie.getTitle())) {
                    movies.set(i, updatedMovie);
                    found = true;
                    break;
                }
            }
            if (found) {
                objectMapper.writeValue(moviesFile, movies);
            }
            return found;
        }
    }

    // 删除电影信息的方法
    public boolean deleteMovie(String title) throws IOException {
        synchronized (lock) {
            List<Movie> movies = loadMovies();
            List<Movie> updatedMovies = movies.stream()
                    .filter(movie -> !movie.getTitle().equals(title))
                    .collect(Collectors.toList());
            boolean deleted = movies.size() != updatedMovies.size();
            if (deleted) {
                objectMapper.writeValue(moviesFile, updatedMovies);
            }
            return deleted;
        }
    }
}