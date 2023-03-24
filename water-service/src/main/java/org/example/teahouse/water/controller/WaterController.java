package org.example.teahouse.water.controller;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.core.error.ResourceNotFoundException;
import org.example.teahouse.water.api.CreateWaterRequest;
import org.example.teahouse.water.api.WaterModel;
import org.example.teahouse.water.repo.Water;
import org.example.teahouse.water.repo.WaterRepository;

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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/waters")
@ExposesResourceFor(WaterModel.class)
@RequiredArgsConstructor
@Tag(name = "Water API")
public class WaterController {
    private final WaterRepository waterRepository;
    private final WaterModelAssembler modelAssembler;
    private final PagedResourcesAssembler<Water> pagedAssembler;

    @GetMapping
    @Operation(summary = "Fetches all of the resources")
    public PagedModel<RepresentationWaterModel> findAll(Pageable pageable) {
        return pagedAssembler.toModel(waterRepository.findAll(pageable), modelAssembler);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetches a resource by its ID")
    public WaterModel findById(@PathVariable UUID id) {
        return waterRepository.findById(id)
            .map(modelAssembler::toModel)
            .orElseThrow(() -> new ResourceNotFoundException("water with id: " + id));
    }

    @GetMapping("/search/findBySize")
    @Operation(summary = "Finds a resource by its size")
    public WaterModel findBySize(@RequestParam("size") String size) {
        return waterRepository.findBySize(size)
            .map(modelAssembler::toModel)
            .orElseThrow(() -> new ResourceNotFoundException("water with size: " + size));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Creates a resource")
    public WaterModel save(@Valid @RequestBody CreateWaterRequest createWaterRequest) {
        return modelAssembler.toModel(waterRepository.save(Water.fromCreateWaterRequest(createWaterRequest)));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes all of the resources")
    public void deleteAll() {
        waterRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deletes a resource by its ID ")
    public void deleteById(@PathVariable UUID id) {
        waterRepository.deleteById(id);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(NOT_FOUND, "Resource not found");
    }
}
