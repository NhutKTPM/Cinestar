package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.CinemaEntity;
import com.da3.MovieTicket.entity.GenreEntity;
import com.da3.MovieTicket.repository.CinemaRepository;
import com.da3.MovieTicket.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public List<GenreEntity> getAllGenres(){
        return genreRepository.findAll();
    }

    public void createGenre(GenreEntity genre){
        genreRepository.save(genre);
    }

    public GenreEntity getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id: " + id));
    }
}
