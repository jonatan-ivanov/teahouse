package org.example.teahouse.proxy;

import io.micrometer.observation.Observation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotaObservationContext extends Observation.Context {

    private long totalSeconds;

    private long totalRequests;

    private double currentRatePerSeconds;

    private double allowedRatePerSeconds;

    private boolean requestAllowed;

}
