package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles all the operation necessary for the authentication of the request sender.
 * The authenticator identifies an accessor by the username and password sent via URI tokens.
 * @author I551381
 * @version 1.0
 */
public class Authenticator {

    /**
     * This map saves all the valid credentials of a user.
     */
    public Map<String, String> getCredentialsMap() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("demo", "123");
        credentials.put("flo", "password");
        return credentials;
    }

    /**
     * This method extract the username from the sent URI using regex pattern.
     * @param uri contains the server address, the request and both tokens (username and password)
     * @return the username from the URI as String.
     */
    public static String extractUsername(String uri) {
        Pattern pattern = Pattern.compile("username=([^&]+)");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * This method extract the password from the sent URI using regex pattern.
     * @param uri contains the server address, the request and both tokens (username and password).
     * @return the password from URI as String.
     */
    public static String extractPassword(String uri) {
        Pattern pattern = Pattern.compile("password=([^&]+)");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * This method checks if the extracted credentials are listed in the credentials map
     * @param credentials A map which listed all the valid users and their password as key value pair.
     * @param username
     * @param password
     * @return returns true if the credentials are listed in the credentials map and false if not
     */
    public static boolean checkCredentials(Map<String, String> credentials, String username, String password) {
        if (credentials.containsKey(username)) {
            return credentials.get(username).equals(password);
        }
        return false;
    }
}
