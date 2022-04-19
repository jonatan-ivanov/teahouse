// Copied from https://github.com/wavefrontHQ/wavefront-spring-boot/blob/master/wavefront-spring-boot/src/main/java/com/wavefront/spring/autoconfigure/WavefrontSleuthBraveSpanHandler.java

package org.example.teahouse.core.wavefront;

import java.io.Closeable;
import java.io.IOException;

import brave.handler.MutableSpan;
import brave.handler.SpanHandler;
import brave.propagation.TraceContext;

import org.springframework.cloud.sleuth.brave.bridge.BraveFinishedSpan;
import org.springframework.cloud.sleuth.brave.bridge.BraveTraceContext;

class HackedWavefrontSleuthBraveSpanHandler extends SpanHandler implements Runnable, Closeable {
    final HackedWavefrontSleuthSpanHandler spanHandler;

    HackedWavefrontSleuthBraveSpanHandler(HackedWavefrontSleuthSpanHandler spanHandler) {
        this.spanHandler = spanHandler;
    }

    @Override
    public boolean end(TraceContext context, MutableSpan span, Cause cause) {
        return spanHandler.end(BraveTraceContext.fromBrave(context), BraveFinishedSpan.fromBrave(span));
    }

    @Override
    public void close() throws IOException {
        this.spanHandler.close();
    }

    @Override
    public void run() {
        this.spanHandler.run();
    }
}
