package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authenticator {

    public Map<String, String> getCredentialsMap() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("demo", "123");
        credentials.put("flo", "password");
        return credentials;
    }

    public static String extractUsername(String uri) {
        Pattern pattern = Pattern.compile("username=([^&]+)");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String extractPassword(String uri) {
        Pattern pattern = Pattern.compile("password=([^&]+)");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static boolean checkCredentials(Map<String, String> credentials, String username, String password) {
        if (credentials.containsKey(username)) {
            return credentials.get(username).equals(password);
        }
        return false;
    }
}
