package org.example.teahouse.proxy;

import io.micrometer.common.docs.KeyName;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;
import io.micrometer.observation.docs.ObservationDocumentation;

public enum QuotaObservation implements ObservationDocumentation {

    /**
     * Observation related to request quota calculation. In case of
     * going over the maximum allowed request rate per second rate limiting
     * will be applied.
     */
    QUOTA_OBSERVATION {
        @Override
        public Class<? extends ObservationConvention<? extends Observation.Context>> getDefaultConvention() {
            return DefaultQuotaObservationConvention.class;
        }

        @Override
        public KeyName[] getLowCardinalityKeyNames() {
            return LowKeys.values();
        }

        @Override
        public KeyName[] getHighCardinalityKeyNames() {
            return HighKeys.values();
        }
    };

    enum LowKeys implements KeyName {

        /**
         * Request was allowed or not. If not then it means
         * that quota was reached.
         */
        ALLOWED {
            @Override
            public String asString() {
                return "quota.allowed";
            }
        }
    }

    enum HighKeys implements KeyName {
        /**
         * Number of accepted requests.
         */
        NO_OF_REQUESTS {
            @Override
            public String asString() {
                return "no.of.accepted.requests";
            }
        },

        /**
         * Total time duration.
         */
        TOTAL_TIME {
            @Override
            public String asString() {
                return "total.time";
            }
        },

        /**
         * Request rate.
         */
        REQUEST_RATE {
            @Override
            public String asString() {
                return "request.rate";
            }
        },

        /**
         * Max allowed request rate.
         */
        MAX_ALLOWED_REQUEST_RATE {
            @Override
            public String asString() {
                return "max.request.rate";
            }
        }
    }
}
