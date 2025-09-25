package org.example.teahouse.tea.api;

import lombok.Builder;

@Builder
public record Tealeaf(String name, String type, String amount) {
}
