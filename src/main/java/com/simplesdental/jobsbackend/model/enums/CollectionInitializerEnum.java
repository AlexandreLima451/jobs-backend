package com.simplesdental.jobsbackend.model.enums;

import java.util.Collection;
import java.util.Collections;

public enum CollectionInitializerEnum {

    LIST ("java.util.List") {
        @Override
        public Collection getEmptyCollection() {
            return Collections.EMPTY_LIST;
        }
    },
    SET ("java.util.Set") {
        @Override
        public Collection getEmptyCollection() {
            return Collections.EMPTY_SET;
        }
    },
    MAP ("java.util.Map") {
        @Override
        public Collection getEmptyCollection() {
            return Collections.singleton(Collections.EMPTY_MAP);
        }
    },
    NONE ("NONE") {
        @Override
        public Collection getEmptyCollection() {
            return null;
        }
    };

    private String type;

    public String getType() {
        return type;
    }

    CollectionInitializerEnum(String type) {
        this.type = type;
    }

    public abstract Collection getEmptyCollection();

    public static CollectionInitializerEnum fromString(String type) {
        for (CollectionInitializerEnum initEnum : CollectionInitializerEnum.values()) {
            if (initEnum.getType().equalsIgnoreCase(type)) {
                return initEnum;
            }
        }
        return NONE;
    }

}
