package org.example.teahouse.tea.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Water {
    private final String amount;
    private final String temperature;
}
