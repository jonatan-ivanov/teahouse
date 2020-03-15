package org.example.teahouse.tealeaf.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.tealeaf.api.CreateTealeafRequest;
import org.example.teahouse.tealeaf.api.TealeafResponse;
import org.example.teahouse.tealeaf.repo.Tealeaf;
import org.example.teahouse.tealeaf.repo.TealeafRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class TealeafController {
    private final TealeafRepository tealeafRepository;

    @GetMapping("/tealeaf")
    public Iterable<TealeafResponse> findAll() {
        return StreamSupport.stream(tealeafRepository.findAll().spliterator(), false)
            .map(Tealeaf::toTealeafResponse)
            .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/tealeaf/{id}")
    public TealeafResponse findById(@PathVariable Long id) {
        return tealeafRepository.findById(id)
            .map(Tealeaf::toTealeafResponse)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No resource found with that id"));
    }

    @GetMapping("/tealeaf/search/findByName")
    public TealeafResponse findByName(@RequestParam("name") String name) {
        return tealeafRepository.findByName(name)
            .map(Tealeaf::toTealeafResponse)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No resource found with that name"));
    }

    @PostMapping("/tealeaf")
    @ResponseStatus(CREATED)
    public TealeafResponse save(@Valid @RequestBody CreateTealeafRequest createTealeafRequest) {
        return tealeafRepository.save(Tealeaf.fromCreateTealeafRequest(createTealeafRequest)).toTealeafResponse();
    }

    @DeleteMapping("/tealeaf")
    @ResponseStatus(NO_CONTENT)
    public void deleteAll() {
        tealeafRepository.deleteAll();
    }

    @DeleteMapping("/tealeaf/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tealeafRepository.deleteById(id);
    }
}
