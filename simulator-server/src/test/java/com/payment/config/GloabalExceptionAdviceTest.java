package com.payment.config;

import com.payment.bo.HttpServletRequestImpl;
import com.payment.simulator.server.exception.GlobalExceptionHandler;
import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentError;
import com.payment.simulator.common.exception.PaymentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @version 1.0
 * @description
 * @date 2022/04/11
 */
@RunWith(SpringRunner.class)
public class GloabalExceptionAdviceTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testPaymentErrorHandler() {
        HttpServletRequest req = new HttpServletRequestImpl();
        Exception e = new PaymentException(ErrorCode.VALIDATE_ERROR, "校验错误");
        ResponseEntity<PaymentError> paymentErrorResponseEntity = globalExceptionHandler.paymentErrorHandler(req, e);
        Assert.assertEquals(paymentErrorResponseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testDefaultErrorHandler() {
        HttpServletRequest req = new HttpServletRequestImpl();
        Exception e = new PaymentException(ErrorCode.VALIDATE_ERROR, "校验错误");
        ResponseEntity<PaymentError> paymentErrorResponseEntity = globalExceptionHandler.defaultErrorHandler(req, e);
        Assert.assertEquals(paymentErrorResponseEntity.getStatusCode(), HttpStatus.OK);
    }
}