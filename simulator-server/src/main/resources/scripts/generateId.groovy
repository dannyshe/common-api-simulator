
def generateId(context) {
    def channelId = context.get("channelId")
    if (channelId == null || channelId.isEmpty()) {
        throw new IllegalArgumentException("channelId cannot be null or empty")
    }
    
    return channelId + "_" + ObjectIdUtil.generateShortUUID()
}

// 执行生成
return generateId(context)