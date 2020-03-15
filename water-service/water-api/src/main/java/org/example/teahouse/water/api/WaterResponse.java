package org.example.teahouse.water.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class WaterResponse {
    private final Long id;
    private final String size;
    private final String amount;
}
