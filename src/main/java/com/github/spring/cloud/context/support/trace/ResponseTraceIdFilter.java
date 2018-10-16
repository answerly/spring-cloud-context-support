package com.github.spring.cloud.context.support.trace;

import brave.Span;
import brave.Tracer;
import com.github.spring.cloud.context.support.trace.Constants;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回tradeId过滤器
 *
 * @author liuyan
 * @date 2018-10-16
 */
public class ResponseTraceIdFilter extends OncePerRequestFilter {

    private Tracer tracer;

    public ResponseTraceIdFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Span span = tracer.currentSpan();
        if (span == null) {
            response.setHeader(Constants.RESPONSE_HEADER_TRADE_ID, "null");
        }
        if (span != null) {
            response.setHeader(Constants.RESPONSE_HEADER_TRADE_ID, span.context().traceIdString());
        }
        filterChain.doFilter(request, response);
    }
}
