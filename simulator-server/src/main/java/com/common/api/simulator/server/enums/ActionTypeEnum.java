package com.common.api.simulator.server.enums;

public enum ActionTypeEnum {
    ASSEMBLE_ONLY,
    ASSEMBLE_AND_CACHE,
    QUERY_CACHE,
    UPDATE_CACHE,
    DELETE_CACHE;

    public static ActionTypeEnum fromString(String value) {
        if (value == null || value.isEmpty()) {
            return ASSEMBLE_ONLY;
        }
        
        try {
            return ActionTypeEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ASSEMBLE_ONLY;
        }
    }
}
