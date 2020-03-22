package org.example.teahouse.water.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/waters")
@ExposesResourceFor(WaterModel.class)
@RequiredArgsConstructor
@Api(tags = "Water API")
public class WaterController {
    private final WaterRepository waterRepository;
    private final WaterModelAssembler modelAssembler;
    private final PagedResourcesAssembler<Water> pagedAssembler;

    @GetMapping
    @ApiOperation("Fetches all of the resources")
    public PagedModel<RepresentationWaterModel> findAll(Pageable pageable) {
        return pagedAssembler.toModel(waterRepository.findAll(pageable), modelAssembler);
    }

    @GetMapping("/{id}")
    @ApiOperation("Fetches a resource by its ID")
    public WaterModel findById(@PathVariable UUID id) {
        return waterRepository.findById(id)
            .map(modelAssembler::toModel)
            .orElseThrow(this::notFound);
    }

    @GetMapping("/search/findBySize")
    @ApiOperation("Finds a resource by its size")
    public WaterModel findBySize(@RequestParam("size") String size) {
        return waterRepository.findBySize(size)
            .map(modelAssembler::toModel)
            .orElseThrow(this::notFound);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Creates a resource")
    public WaterModel save(@Valid @RequestBody CreateWaterRequest createWaterRequest) {
        return modelAssembler.toModel(waterRepository.save(Water.fromCreateWaterRequest(createWaterRequest)));
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletes all of the resources")
    public void deleteAll() {
        waterRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletes a resource by its ID ")
    public void deleteById(@PathVariable UUID id) {
        waterRepository.deleteById(id);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(NOT_FOUND, "Resource not found");
    }
}
