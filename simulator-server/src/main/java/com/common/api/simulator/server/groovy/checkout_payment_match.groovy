package com.common.api.simulator.server.groovy

def rule_script() {
    def amount = context.requestBody.amount
    if (amount <= 100) {
        return true;
    }
    return false
}