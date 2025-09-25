package org.example.teahouse.tea.api;

import lombok.Builder;

@Builder
public record TeaResponse(Water water, Tealeaf tealeaf, String steepingTime) {
}
