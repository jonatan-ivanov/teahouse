package org.example.teahouse.water.observation._04_convention;

import io.micrometer.common.docs.KeyName;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;
import io.micrometer.observation.docs.ObservationDocumentation;

public enum WaterObservationDocumentation implements ObservationDocumentation {

    /**
     * Observation around finding by size.
     */
    FIND_BY_SIZE {
        @Override
        public Class<? extends ObservationConvention<? extends Observation.Context>> getDefaultConvention() {
            return DefaultWaterObservationConvention.class;
        }

        @Override
        public KeyName[] getLowCardinalityKeyNames() {
            return LowCardinalityKeys.values();
        }

        @Override
        public KeyName[] getHighCardinalityKeyNames() {
            return HighCardinalityKeys.values();
        }

        @Override
        public Observation.Event[] getEvents() {
            return Events.values();
        }
    };

    enum LowCardinalityKeys implements KeyName {

        /**
         * Describes the environment where water service was running.
         */
        ENVIRONMENT {
            @Override
            public String asString() {
                return "environment";
            }
        }
    }

    enum HighCardinalityKeys implements KeyName {

        /**
         * Describes the size by which water was searched for.
         */
        WATER_SIZE {
            @Override
            public String asString() {
                return "water.size";
            }
        }
    }

    enum Events implements Observation.Event {

        /**
         * Event when water was found.
         */
        WATER_CALCULATED {
            @Override
            public String getName() {
                return "calculated";
            }

            @Override
            public String getContextualName() {
                return "water.calculated";
            }
        }
    }
}
