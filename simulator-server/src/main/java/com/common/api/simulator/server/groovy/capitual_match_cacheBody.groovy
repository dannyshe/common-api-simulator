package com.common.api.simulator.server.groovy

import org.apache.commons.lang3.StringUtils

def rule_script() {
    String orderId = context.pathValueMap.orderId;
    if (StringUtils.endsWith(orderId, "0014")) {
        return true
    }
    return false
}