package com.github.yeecode.matrixauth.server.constant;

import java.util.Arrays;

public enum RoleType {
    InterfaceControlled {
        @Override
        public RequestSource[] getSupportedRequestSources() {
            return new RequestSource[]{RequestSource.Interface};
        }
    },

    BusinessAppControlled {
        @Override
        public RequestSource[] getSupportedRequestSources() {
            return new RequestSource[]{RequestSource.BusinessApp};
        }
    },
    InterfaceAndBusinessAppControlled {
        @Override
        public RequestSource[] getSupportedRequestSources() {
            return new RequestSource[]{RequestSource.Interface, RequestSource.BusinessApp};
        }
    };

    abstract RequestSource[] getSupportedRequestSources();

    public Boolean isSupportedRequestSources(RequestSource requestSource) {
        return Arrays.asList(getSupportedRequestSources()).contains(requestSource);
    }
}
