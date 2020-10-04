package org.example.teahouse.tealeaf.controller;

import static org.springframework.hateoas.IanaLinkRelations.COLLECTION;
import static org.springframework.hateoas.IanaLinkRelations.SEARCH;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.example.teahouse.tealeaf.api.TealeafModel;
import org.example.teahouse.tealeaf.repo.Tealeaf;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TealeafModelAssembler extends RepresentationModelAssemblerSupport<Tealeaf, RepresentationTealeafModel> {
    private final EntityLinks links;

    public TealeafModelAssembler(EntityLinks entityLinks) {
        super(TealeafController.class, RepresentationTealeafModel.class);
        this.links = entityLinks;
    }

    @Override
    protected RepresentationTealeafModel instantiateModel(Tealeaf tealeaf) {
        return tealeaf.toRepresentationTealeafModel();
    }

    @Override
    public RepresentationTealeafModel toModel(Tealeaf tealeaf) {
        return createModelWithId(tealeaf.getId(), tealeaf)
            .add(linkTo(methodOn(TealeafController.class).findByName(tealeaf.getName())).withRel(SEARCH))
            .add(links.linkToCollectionResource(TealeafModel.class).withRel(COLLECTION));
    }
}
