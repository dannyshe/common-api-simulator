package com.payment.simulator.server.exception;

import com.payment.simulator.common.enums.PaymentSystem;
import com.payment.simulator.common.exception.ErrorCode;
import com.payment.simulator.common.exception.PaymentError;
import com.payment.simulator.common.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @date 2022/04/10
 */
@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

    /**
     * paymentException处理
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler({PaymentException.class})
    public ResponseEntity<PaymentError> paymentErrorHandler(HttpServletRequest req, Exception e) {
        PaymentException exception = (PaymentException) e;
        log.error("catch global PaymentException with url:{},msg:{}", req.getRequestURI(),
                exception.getInternalMessage(), e);
        return new ResponseEntity<>(exception.toPaymentError(), null, HttpStatus.OK);
    }

    /**
     * 兜底处理
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<PaymentError> defaultErrorHandler(HttpServletRequest req, Exception ex) {
        log.error("catch global exception with url:{}", req.getRequestURI(), ex);
        PaymentError result = new PaymentError(ErrorCode.SERVER_ERROR.getCode(),
                ErrorCode.SERVER_ERROR.getMsg(), PaymentSystem.PAYMENT_UNKNOWN.getSystemId());
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }
}
