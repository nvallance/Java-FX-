package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginData {

    private Map<String, String> loginData = new HashMap<>();

    public LoginData() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("login.txt"));
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] loginEls = data.split(" ");
                loginData.put(loginEls[0], loginEls[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean login(String userName, String password) {
        if (loginData.containsKey(userName) && loginData.get(userName).equals(password)) {
            return true;
        }
        return false;
    }
}
