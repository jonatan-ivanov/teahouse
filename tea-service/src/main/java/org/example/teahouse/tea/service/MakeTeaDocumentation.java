package org.example.teahouse.tea.service;

import io.micrometer.common.docs.KeyName;
import io.micrometer.common.lang.NonNull;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;
import io.micrometer.observation.docs.ObservationDocumentation;

public enum MakeTeaDocumentation implements ObservationDocumentation {
    /**
     * Make some tea.
     */
    MAKE_TEA {
        @Override
        public Class<? extends ObservationConvention<? extends Observation.Context>> getDefaultConvention() {
            return DefaultMakeTeaConvention.class;
        }

        @NonNull
        @Override
        public KeyName[] getLowCardinalityKeyNames() {
            return LowCardinalityKeyNames.values();
        }
    };

    enum LowCardinalityKeyNames implements KeyName {
        /**
         * Name that uniquely identifies a tea-leaf.
         */
        TEA_LEAF_NAME {
            @NonNull
            @Override
            public String asString() {
                return "tea.name";
            }
        },
        /**
         * Water size (small, medium, large).
         */
        WATER_SIZE {
            @NonNull
            @Override
            public String asString() {
                return "water.size";
            }
        }
    }
}
