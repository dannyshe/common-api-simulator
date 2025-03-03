def rule_script() {
    def path = context.requestPath;
    def reqBody = context.requestBody;
    def reqParam = context.requestParam;
    return path.equals("/v1/channel/recharge")&&reqParam.fiat.equals("USD")
}