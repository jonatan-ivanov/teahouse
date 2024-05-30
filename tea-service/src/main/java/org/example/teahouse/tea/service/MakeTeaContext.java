package org.example.teahouse.tea.service;

import io.micrometer.observation.Observation;

public class MakeTeaContext extends Observation.Context {
    private final String teaName;
    private final String teaSize;

    MakeTeaContext(String teaName, String teaSize) {
        this.teaName = teaName;
        this.teaSize = teaSize;
    }

    String getTeaName() {
        return teaName;
    }

    String getTeaSize() {
        return teaSize;
    }
}
