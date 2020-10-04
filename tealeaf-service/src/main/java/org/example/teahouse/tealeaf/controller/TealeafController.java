package org.example.teahouse.tealeaf.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tealeaves")
@ExposesResourceFor(TealeafModel.class)
@Api(tags = "Tealeaf API")
public class TealeafController {
    private final TealeafRepository tealeafRepository;
    private final TealeafModelAssembler modelAssembler;
    private final PagedResourcesAssembler<Tealeaf> pagedAssembler;

    @GetMapping
    @ApiOperation("Fetches all of the resources")
    public PagedModel<RepresentationTealeafModel> findAll(Pageable pageable) {
        return pagedAssembler.toModel(tealeafRepository.findAll(pageable), modelAssembler);
    }

    @GetMapping("/{id}")
    @ApiOperation("Fetches a resource by its ID")
    public TealeafModel findById(@PathVariable UUID id) {
        return tealeafRepository.findById(id)
            .map(modelAssembler::toModel)
            .orElseThrow(this::notFound);
    }

    @GetMapping("/search/findByName")
    @ApiOperation("Finds a resource by its name")
    public TealeafModel findByName(@RequestParam("name") String name) {
        return tealeafRepository.findByName(name)
            .map(modelAssembler::toModel)
            .orElseThrow(this::notFound);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Creates a resource")
    public TealeafModel save(@Valid @RequestBody CreateTealeafRequest createTealeafRequest) {
        return modelAssembler.toModel(tealeafRepository.save(Tealeaf.fromCreateTealeafRequest(createTealeafRequest)));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletes all of the resources")
    public void deleteAll() {
        tealeafRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletes a resource by its ID ")
    public void deleteById(@PathVariable UUID id) {
        tealeafRepository.deleteById(id);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(NOT_FOUND, "Resource not found");
    }
}
