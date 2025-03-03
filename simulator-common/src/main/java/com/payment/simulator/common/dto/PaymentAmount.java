package com.payment.simulator.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Danny She
 * @createTime 2021-11-01
 */
public class PaymentAmount implements Serializable {
    private static final long serialVersionUID = 6223263367164043850L;
    private BigDecimal amount;
    private String currency;

    public PaymentAmount() {
    }

    public PaymentAmount(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
