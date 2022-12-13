package org.example.teahouse.core.error;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {
    private final Tracer tracer;

    public CommonExceptionHandler(Tracer tracer) {
        this.tracer = tracer;
    }

    @ExceptionHandler(Throwable.class)
    ProblemDetail handleThrowable(HttpServletRequest request, Throwable error) {
        logger.error(ExceptionUtils.getStackTrace(error));
        request.setAttribute(ERROR_EXCEPTION_ATTRIBUTE, error);

        return createProblemDetail(INTERNAL_SERVER_ERROR, error);
    }

    private ProblemDetail createProblemDetail(HttpStatus status, Throwable error) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setDetail(error.toString());
        problemDetail.setProperty("series", status.series());
        problemDetail.setProperty("rootCause", ExceptionUtils.getRootCause(error).toString());
        problemDetail.setProperty("traceparent", getTraceParent());

        return problemDetail;
    }

    private String getTraceParent() {
        Span span = tracer.currentSpan();
        if (span != null) {
            String sampledFlag = span.context().sampled() ? "01" : "00";
            return "00-%s-%s-%s".formatted(span.context().traceId(), span.context().spanId(), sampledFlag);
        }
        else {
            return  "00-00000000000000000000000000000000-0000000000000000-00";
        }
    }
}
