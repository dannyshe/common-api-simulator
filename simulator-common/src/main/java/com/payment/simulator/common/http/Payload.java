package com.payment.simulator.common.http;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.MapUtils;
import org.slf4j.MDC;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentException;

public interface Payload<Req, Res> {

    String TRACE_ID = "X-TraceId";

    default Req getReq() {
        return null;
    }

    URI getUri();

    default HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    default HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        final Map<String, String> originalContextCopy = MDC.getCopyOfContextMap();
        if (MapUtils.isEmpty(originalContextCopy)) {
            return headers;
        }
        for (Map.Entry<String, String> entry : originalContextCopy.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    default HttpEntity<Req> getHttpEntity() {
        HttpHeaders httpHeaders = getHttpHeaders();
        return new HttpEntity<>(getReq(), httpHeaders);
    }

    default ParameterizedTypeReference<Res> getResponseType() {
        return new ParameterizedTypeReference<Res>() {
        };
    }

    default void handleHttpStatus(ResponseEntity<Res> responseEntity) {
        if (!Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK)) {
            throw new PaymentException(ErrorCode.STATUS_ERROR);
        }
    }

    default void handleHttpBizStatus(Res res) {
        if (Objects.isNull(res)) {
            throw new PaymentException(ErrorCode.STATUS_ERROR);
        }
    }
}
