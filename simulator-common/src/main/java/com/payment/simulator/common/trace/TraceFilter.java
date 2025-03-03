package com.payment.simulator.common.trace;

import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.Tracer;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;

/**
 * 描述信息
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
public class TraceFilter implements Filter {

    @Autowired(required = false)
    private Tracer tracer;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (tracer != null && tracer.activeSpan() != null) {
            JaegerSpanContext ctx = (JaegerSpanContext) tracer.activeSpan().context();
            MDC.put("traceId",ctx.getTraceId());
            tracer.activeSpan().setTag("x-traceid", ctx.getTraceId());
        }
        //执行
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.remove("traceId");
    }

    @Override
    public void destroy() {

    }

}
