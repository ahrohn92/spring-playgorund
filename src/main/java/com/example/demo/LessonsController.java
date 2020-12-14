package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @PatchMapping("{id}")
    public Lesson update(@PathVariable long id, @RequestBody Map<String, String> body) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(body.get("deliveredOn"));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);

        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle(body.get("title"));
        lesson.setDeliveredOn(c.getTime());

        return this.repository.save(lesson);
    }
}