package com.payment.simulator.server.groovy

import com.alibaba.fastjson.JSONObject

def rule_script() {
    def value = context.requestBody.amount
    if (value > 1000) {
        JSONObject extraData = context.extraData
        extraData.put("paymentIdKey", "id")
        extraData.put("paymentIdValue", UUID.randomUUID().toString())
        return true;
    }
    return false
}

def rule_script1() {
    if (context.cacheBody.status == "Authorized") {
        return true
    }
    return false
}