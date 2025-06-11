package net.proselyte.publicapi.filter;

import io.opentelemetry.api.trace.Span;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TraceIdToMdcFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var ctx = Span.current().getSpanContext();
        if (ctx.isValid()) {
            MDC.put("trace_id", ctx.getTraceId());
            MDC.put("span_id", ctx.getSpanId());
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("trace_id");
            MDC.remove("span_id");
        }
    }
}
