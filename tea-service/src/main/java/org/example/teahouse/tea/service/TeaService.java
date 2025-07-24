package org.example.teahouse.tea.service;

import java.util.Collection;

import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;

public interface TeaService {
    TeaResponse make(String name, String size);

    Collection<SimpleTealeafModel> tealeaves();

    Collection<SimpleWaterModel> waters();
}
