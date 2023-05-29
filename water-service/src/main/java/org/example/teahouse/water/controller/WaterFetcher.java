package org.example.teahouse.water.controller;

import java.util.Optional;

import org.example.teahouse.water.repo.Water;

public interface WaterFetcher {
    Optional<Water> findBySize(String size);
}
