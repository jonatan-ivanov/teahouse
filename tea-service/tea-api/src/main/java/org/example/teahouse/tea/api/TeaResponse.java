package org.example.teahouse.tea.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class TeaResponse {
    private final Water water;
    private final Tealeaf tealeaf;
    private final String steepingTime;
}
