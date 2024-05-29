package org.example.teahouse.proxy;

import io.micrometer.common.KeyValues;

import static java.lang.String.valueOf;
import static org.example.teahouse.proxy.QuotaObservation.HighKeys.MAX_ALLOWED_REQUEST_RATE;
import static org.example.teahouse.proxy.QuotaObservation.HighKeys.NO_OF_REQUESTS;
import static org.example.teahouse.proxy.QuotaObservation.HighKeys.REQUEST_RATE;
import static org.example.teahouse.proxy.QuotaObservation.HighKeys.TOTAL_TIME;

public class DefaultQuotaObservationConvention implements QuotaObservationConvention {

    static final DefaultQuotaObservationConvention INSTANCE = new DefaultQuotaObservationConvention();

    @Override
    public KeyValues getLowCardinalityKeyValues(QuotaObservationContext context) {
        return KeyValues.of(QuotaObservation.LowKeys.ALLOWED.withValue(valueOf(context.isRequestAllowed())));
    }

    @Override
    public KeyValues getHighCardinalityKeyValues(QuotaObservationContext context) {
        return KeyValues.of(MAX_ALLOWED_REQUEST_RATE.withValue(valueOf(context.getAllowedRatePerSeconds())), NO_OF_REQUESTS.withValue(valueOf(context.getTotalRequests())), REQUEST_RATE.withValue(valueOf(context.getCurrentRatePerSeconds())), TOTAL_TIME.withValue(valueOf(context.getTotalSeconds())));
    }

    @Override
    public String getName() {
        return "request.quota.calculation";
    }

    @Override
    public String getContextualName(QuotaObservationContext context) {
        return "quota";
    }
}
