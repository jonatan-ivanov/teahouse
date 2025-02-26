package org.example.teahouse.tea.service;

import java.util.Collection;

import io.micrometer.common.lang.Nullable;
import io.micrometer.observation.ObservationRegistry;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;

public class ObservedTeaService implements TeaService {
    private final static MakeTeaConvention DEFAULT_CONVENTION = new DefaultMakeTeaConvention();
    private final TeaService delegate;
    private final ObservationRegistry registry;
    @Nullable
    private final MakeTeaConvention customConvention;

    public ObservedTeaService(TeaService delegate, ObservationRegistry registry) {
        this(delegate, registry, null);
    }

    public ObservedTeaService(TeaService delegate, ObservationRegistry registry, @Nullable MakeTeaConvention customConvention) {
        this.delegate = delegate;
        this.registry = registry;
        this.customConvention = customConvention;
    }

    @Override
    public TeaResponse make(String name, String size) {
//        return Observation.createNotStarted("make.tea", registry)
//            .lowCardinalityKeyValue("tea.name", name)
//            .lowCardinalityKeyValue("water.size", size)
//            .observe(() -> delegate.make(name, size));

//        return Observation.createNotStarted(
//            customConvention,
//            DEFAULT_CONVENTION,
//            () -> new MakeTeaContext(name, size),
//            registry)
//            .observe(() -> delegate.make(name, size));

        return MakeTeaDocumentation.MAKE_TEA.observation(
            customConvention,
            DEFAULT_CONVENTION,
            () -> new MakeTeaContext(name, size),
            registry)
            .observe(() -> delegate.make(name, size));
    }

    @Override
    public Collection<SimpleTealeafModel> tealeaves() {
        return delegate.tealeaves();
    }

    @Override
    public Collection<SimpleWaterModel> waters() {
        return delegate.waters();
    }
}
