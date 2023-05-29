package org.example.teahouse.water.repo;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.controller.WaterFetcher;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class RepositoryWaterFetcher implements WaterFetcher {

    private final WaterRepository waterRepository;

    @Override
    public Optional<Water> findBySize(String size) {
        return waterRepository.findBySize(size);
    }
}
