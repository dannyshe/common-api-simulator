package com.payment.simulator.common.dto.response;

import com.payment.simulator.common.enums.PaymentSystem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述信息
 *
 * @author Grayson
 * @createTime 2021-11-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {
    /**
     * 系统调用码
     */
    private String code;
    /**
     * 系统调用描述
     */
    private String msg;

    private String systemId;
    /**
     * 业务返回结果
     */
    private T data;

    public static <T> ResponseWrapper<T> success(T data) {
        return ResponseWrapper.<T>builder()
                .systemId(PaymentSystem.MERCHANT_ACCOUNT.getSystemId())
                .code("1000")
                .msg("成功")
                .data(data)
                .build();
    }

    public static <T> ResponseWrapper<T> success() {
        return ResponseWrapper.<T>builder()
                .systemId(PaymentSystem.MERCHANT_ACCOUNT.getSystemId())
                .code("1000")
                .msg("成功")
                .data(null)
                .build();
    }


    public static <T> ResponseWrapper<T> fail(String code, String message) {
        return fail(code, message, null);
    }

    public static <T> ResponseWrapper<T> fail(String code, String message, T data) {
        return ResponseWrapper.<T>builder()
                .systemId(PaymentSystem.MERCHANT_ACCOUNT.getSystemId())
                .code(code)
                .msg(message)
                .data(data)
                .build();
    }
}
