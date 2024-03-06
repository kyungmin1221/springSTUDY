package com.sparta.homework3.constant;

public enum Role {
//    MANAGER,STAFF;

    MANAGER(Authority.MANAGER),
    STAFF(Authority.STAFF);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MANAGER = "MANAGER";
        public static final String STAFF = "STAFF";
    }
}
