package org.example.teahouse.tealeaf.controller;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.core.error.ResourceNotFoundException;
import org.example.teahouse.tealeaf.api.CreateTealeafRequest;
import org.example.teahouse.tealeaf.api.TealeafModel;
import org.example.teahouse.tealeaf.repo.Tealeaf;
import org.example.teahouse.tealeaf.repo.TealeafRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tealeaves")
@ExposesResourceFor(TealeafModel.class)
@Tag(name = "Tealeaf API")
public class TealeafController {
    private final TealeafRepository tealeafRepository;
    private final TealeafModelAssembler modelAssembler;
    private final PagedResourcesAssembler<Tealeaf> pagedAssembler;

    @GetMapping
    @Operation(summary = "Fetches all of the resources")
    public PagedModel<RepresentationTealeafModel> findAll(Pageable pageable) {
        return pagedAssembler.toModel(tealeafRepository.findAll(pageable), modelAssembler);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetches a resource by its ID")
    public TealeafModel findById(@PathVariable UUID id) {
        return tealeafRepository.findById(id)
            .map(modelAssembler::toModel)
            .orElseThrow(() -> new ResourceNotFoundException("tea-leaf with id: " + id));
    }

    @GetMapping("/search/findByName")
    @Operation(summary = "Finds a resource by its name")
    public TealeafModel findByName(@RequestParam("name") String name) {
        return tealeafRepository.findByName(name)
            .map(modelAssembler::toModel)
            .orElseThrow(() -> new ResourceNotFoundException("tea-leaf with name: " + name));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Creates a resource")
    public TealeafModel save(@Valid @RequestBody CreateTealeafRequest createTealeafRequest) {
        return modelAssembler.toModel(tealeafRepository.save(Tealeaf.fromCreateTealeafRequest(createTealeafRequest)));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes all of the resources")
    public void deleteAll() {
        tealeafRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes a resource by its ID ")
    public void deleteById(@PathVariable UUID id) {
        tealeafRepository.deleteById(id);
    }
}
