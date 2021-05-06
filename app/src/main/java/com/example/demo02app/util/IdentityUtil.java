package com.example.demo02app.util;

/**
 * 用户权限
 */
public class IdentityUtil {
    /***
     * 员工
     */
    public static final int IDENTITY_EMPLOYEE = 0x01;
    /**
     * 管理员
     */
    public static final int IDENTITY_ADMIN = 0x02;

    public static String getIdentityName(int identity) {
        switch (identity) {
            default:
            case IDENTITY_EMPLOYEE:
                return "员工";
            case IDENTITY_ADMIN:
                return "管理员";
        }
    }

    public static int getIdentity(String identityName) {
        switch (identityName) {
            default:
            case "员工":
                return IDENTITY_EMPLOYEE;
            case "管理员":
                return IDENTITY_ADMIN;
        }
    }
}
