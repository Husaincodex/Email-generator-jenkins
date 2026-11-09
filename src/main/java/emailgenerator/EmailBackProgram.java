package emailgenerator;

import java.security.SecureRandom;
import java.util.Locale;

public class EmailBackProgram {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String firstname;
    private final String lastname;
    private final String password;
    private final String department;
    private final String email;
    private final int mailboxCapacity = 500;

    private static final String PASSWORD_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*^";

    public EmailBackProgram(String firstname, String lastname, String department) {

        this.firstname = firstname.trim();
        this.lastname = lastname.trim();
        this.department = department;

        this.password = randomPassword(10);

        String first = this.firstname.toLowerCase(Locale.ROOT);
        String last = this.lastname.toLowerCase(Locale.ROOT);

        String departmentDomain = switch (department) {
            case "C++" -> "cpp";
            case "Java" -> "java";
            case "Python" -> "python";
            default -> "";
        };

        if (departmentDomain.isEmpty()) {
            this.email = first + "." + last + "@xyzemail.com";
        } else {
            this.email = first + "." + last + "@"
                    + departmentDomain + ".xyzemail.com";
        }
    }

    private String randomPassword(int length) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(PASSWORD_CHARACTERS.length());
            result.append(PASSWORD_CHARACTERS.charAt(index));
        }

        return result.toString();
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return firstname + " " + lastname;
    }

    public int getMailboxCapacity() {
        return mailboxCapacity;
    }
}
