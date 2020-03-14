package org.example.teahouse.water.api;

import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class CreateWaterRequest {
    @Size(min = 1, max = 10)
    private final String size;

    @Size(min = 1, max = 10)
    private final String amount;
}
