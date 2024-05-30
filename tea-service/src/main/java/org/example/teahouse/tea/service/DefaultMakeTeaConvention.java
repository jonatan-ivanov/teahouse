package org.example.teahouse.tea.service;

import io.micrometer.common.KeyValues;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;

public class DefaultMakeTeaConvention implements MakeTeaConvention {

    @Nullable
    @Override
    public String getName() {
        return "make.tea";
    }

    @NonNull
    @Override
    public KeyValues getLowCardinalityKeyValues(MakeTeaContext context) {
        return KeyValues.of(
            "tea.name", context.getTeaName(),
            "water.size", context.getTeaSize()
        );

        // with "MakeTeaDocumentation"
//        return KeyValues.of(
//            MakeTeaDocumentation.LowCardinalityKeyNames.TEALEAF_NAME.withValue(context.getTeaName()),
//            MakeTeaDocumentation.LowCardinalityKeyNames.WATER_SIZE.withValue(context.getTeaSize())
//        );
    }
}
