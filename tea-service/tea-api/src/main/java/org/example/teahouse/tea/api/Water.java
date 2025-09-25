package org.example.teahouse.tea.api;

import lombok.Builder;

@Builder
public record Water(String amount, String temperature) {
}
