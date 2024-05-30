package org.example.teahouse.tea.service;

import io.micrometer.observation.Observation;

public class MakeTeaContext extends Observation.Context {
    private final String teaName;
    private final String teaSize;

    public MakeTeaContext(String teaName, String teaSize) {
        this.teaName = teaName;
        this.teaSize = teaSize;
    }

    public String getTeaName() {
        return teaName;
    }

    public String getTeaSize() {
        return teaSize;
    }
}
