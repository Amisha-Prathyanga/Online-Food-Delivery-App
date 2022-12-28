
package com.example.foodapp;

import android.provider.BaseColumns;

public final class UserTable {
    private UserTable(){};

    public static class Users implements BaseColumns{
        public static final String USER_TABLE_NAME = "Users";
        public static final String ID = "ID";
        public static final String COLUMN_FULL_NAME = "FullName";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_MOBILE = "Mobile";
        public static final String COLUMN_BIRTHDATE = "BirthDate";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_REG_DATE = "Registered_Date";
        public static final String COLUMN_TYPE = "Type";
    }
}

