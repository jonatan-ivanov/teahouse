package org.example.teahouse.tea.service;

import io.micrometer.observation.Observation;

public class MakeTeaContext extends Observation.Context {
    private final String teaName;
    private final String waterSize;

    MakeTeaContext(String teaName, String waterSize) {
        this.teaName = teaName;
        this.waterSize = waterSize;
    }

    String getTeaName() {
        return teaName;
    }

    String getWaterSize() {
        return waterSize;
    }
}
