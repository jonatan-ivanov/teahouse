package org.example.teahouse.tea.service;

import jdk.jfr.Label;
import org.example.teahouse.core.observation.jfr.ObservedEvent;
import org.example.teahouse.core.observation.jfr.ContextToEventMapper;

public class MakeTeaContextToEventMapper implements ContextToEventMapper<MakeTeaContext> {

    @Override
    public ObservedEvent<MakeTeaContext> map(MakeTeaContext context) {
        return new MakeTeaEvent(context);
    }

    @Override
    public Class<MakeTeaContext> supportedContextType() {
        return MakeTeaContext.class;
    }

    //@Category("Business")
    @Label("MakeTeaEvent")
    static class MakeTeaEvent extends ObservedEvent<MakeTeaContext> {
        private String teaName;
        private String waterSize;

        private MakeTeaEvent(MakeTeaContext context) {
            super(context);
        }

        @Override
        public void enhance(MakeTeaContext context) {
            this.teaName = context.getTeaName();
            this.waterSize = context.getWaterSize();
        }
    }
}
