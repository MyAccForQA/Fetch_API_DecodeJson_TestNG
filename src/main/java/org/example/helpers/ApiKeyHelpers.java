package org.example.helpers;

public class ApiKeyHelpers {
    // used class to save API keys.
    // in company need to use Env. for it.
    private final static String Default = "33dbdae779150aba3047ef6ba25ffad0";
    private final static String MyFirstKey = "7ff5e4c413e617fd707fa62907a1d63d";

    public static String getDefault() {
        return Default;
    }

    public static String getMyFirstKey() {
        return MyFirstKey;
    }
}