package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) {
        try {
            movieService.saveMovie(movie);
            return ResponseEntity.ok("电影保存成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("保存失败：" + e.getMessage());
        }
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            List<Movie> movies = movieService.loadMovies();
            return ResponseEntity.ok(movies);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/movies/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        try {
            Optional<Movie> movie = movieService.findMovieByTitle(title);
            return movie.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 更新电影信息的接口
    @PatchMapping("/movies")
    public ResponseEntity<String> partialUpdateMovie(@RequestBody MovieUpdateDto updateDto) {
        try {
            // 校验必填字段
            if (updateDto.getTitle() == null) {
                return ResponseEntity.badRequest().body("必须提供电影标题");
            }

            // 查询原始电影对象
            Optional<Movie> optionalMovie = movieService.findMovieByTitle(updateDto.getTitle());
            if (optionalMovie.isEmpty()) {
                return new ResponseEntity<>("未找到该电影，更新失败！", HttpStatus.NOT_FOUND);
            }

            // 获取原始电影对象
            Movie existingMovie = optionalMovie.get();

            // 应用更新（只更新DTO中提供的非空字段）
            if (updateDto.getPosterPath() != null) {
                existingMovie.setPosterPath(updateDto.getPosterPath());
            }
            if (updateDto.getStars() != null) {
                existingMovie.setStars(updateDto.getStars());
            }
            if (updateDto.getDirector() != null) {
                existingMovie.setDirector(updateDto.getDirector());
            }
            if (updateDto.getType() != null) {
                existingMovie.setType(updateDto.getType());
            }
            if (updateDto.getRegion() != null) {
                existingMovie.setRegion(updateDto.getRegion());
            }
            if (updateDto.getLevel() != null) {
                existingMovie.setLevel(updateDto.getLevel());
            }
            if (updateDto.getDownloadLink() != null) {
                existingMovie.setDownloadLink(updateDto.getDownloadLink());
            }
            if (updateDto.getWatchLink() != null) {
                existingMovie.setWatchLink(updateDto.getWatchLink());
            }
            if (updateDto.getSynopsis() != null) {
                existingMovie.setSynopsis(updateDto.getSynopsis());
            }

            // 保存更新后的电影
            boolean success = movieService.updateMovie(existingMovie);
            if (success) {
                return ResponseEntity.ok("电影信息更新成功！");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败，未知错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败：" + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("参数错误：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败：未知异常");
        }
    }

    // 删除电影信息的接口
    @DeleteMapping("/movies/{title}")
    public ResponseEntity<String> deleteMovie(@PathVariable String title) {
        try {
            boolean success = movieService.deleteMovie(title);
            if (success) {
                return ResponseEntity.ok("电影删除成功！");
            } else {
                // 直接构建 ResponseEntity 对象
                return new ResponseEntity<>("未找到该电影，删除失败！", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("删除失败：" + e.getMessage());
        }
    }
}