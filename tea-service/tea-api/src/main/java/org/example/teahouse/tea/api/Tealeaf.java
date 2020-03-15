package org.example.teahouse.tea.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Tealeaf {
    private final String name;
    private final String type;
    private final String amount;
}
