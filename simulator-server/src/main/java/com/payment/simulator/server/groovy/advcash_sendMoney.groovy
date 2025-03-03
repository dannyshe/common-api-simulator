package com.payment.simulator.server.groovy

import org.apache.commons.collections.CollectionUtils

def rule_script() {
    List list = context.requestBody.Body.sendMoney
    return CollectionUtils.isNotEmpty(list) && list.get(0).get("sendMoney") != null
}