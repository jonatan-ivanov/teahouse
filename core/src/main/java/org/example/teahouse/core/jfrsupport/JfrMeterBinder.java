/*
 * Copyright 2025 VMware, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.teahouse.core.jfrsupport;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import jdk.jfr.consumer.RecordingStream;

import java.io.Closeable;
import java.time.Duration;

public abstract class JfrMeterBinder implements MeterBinder, Closeable {

    private final RecordingStream recordingStream;

    public JfrMeterBinder() {
        this(new RecordingStream());
    }

    public JfrMeterBinder(RecordingStream recordingStream) {
        this.recordingStream = recordingStream;
        this.configure(this.recordingStream);
        this.recordingStream.startAsync();
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        register(registry, recordingStream);
    }

    protected abstract void register(MeterRegistry registry, RecordingStream recordingStream);

    protected void configure(RecordingStream recordingStream) {
        recordingStream.setMaxAge(Duration.ofSeconds(5));
        recordingStream.setMaxSize(10L * 1024 * 1024);
    }

    @Override
    public void close() {
        recordingStream.close();
    }

}
