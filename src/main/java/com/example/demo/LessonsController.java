package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/lessons")
public class LessonsController {

    private final LessonRepository repository;

    public LessonsController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @GetMapping("/lesson/{id}")
    public Optional<Lesson> read(@PathVariable long id) {
        return this.repository.findById(id);
    }

    @DeleteMapping("/lesson/{id}")
    public void delete(@PathVariable long id) {
        this.repository.deleteById(id);
    }

}