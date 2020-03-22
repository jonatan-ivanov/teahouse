package org.example.teahouse.water.controller;

import static org.springframework.hateoas.IanaLinkRelations.COLLECTION;
import static org.springframework.hateoas.IanaLinkRelations.SEARCH;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.example.teahouse.water.api.WaterModel;
import org.example.teahouse.water.repo.Water;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class WaterModelAssembler extends RepresentationModelAssemblerSupport<Water, RepresentationWaterModel> {
    private final EntityLinks links;

    public WaterModelAssembler(EntityLinks entityLinks) {
        super(WaterController.class, RepresentationWaterModel.class);
        this.links = entityLinks;
    }

    @Override
    protected RepresentationWaterModel instantiateModel(Water water) {
        return water.toRepresentationWaterModel();
    }

    @Override
    public RepresentationWaterModel toModel(Water water) {
        return createModelWithId(water.getId(), water)
            .add(linkTo(methodOn(WaterController.class).findBySize(water.getSize())).withRel(SEARCH))
            .add(links.linkToCollectionResource(WaterModel.class).withRel(COLLECTION));
    }
}
