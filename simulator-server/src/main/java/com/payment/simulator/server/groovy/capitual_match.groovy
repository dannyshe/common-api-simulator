package com.payment.simulator.server.groovy

def rule_script() {
    def value = context.requestBody.value
    if (value.toDouble() <= 10.00) {
        return true;
    }
    return false


}