package com.payment.simulator.common.http;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    public <REQ, RES> RES execute(Payload<REQ, RES> payload) {
        ResponseEntity result = null;
        try {
            result = restTemplate.exchange(payload.getUri(), payload.getHttpMethod(), payload.getHttpEntity(), payload.getResponseType());
        } catch (RestClientException e) {
            handleRequestException(payload.getUri(), e);
        }
        payload.handleHttpStatus(result);
        RES body = (RES) result.getBody();
        payload.handleHttpBizStatus(body);
        return body;
    }

    private void handleRequestException(URI uri, RestClientException e) {
        log.error("http call url:{} error, error msg:{}", uri, e.getMessage(), e);
        throw new PaymentException(ErrorCode.SERVER_ERROR);
    }


}
