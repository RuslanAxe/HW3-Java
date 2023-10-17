import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

class User {
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private char gender;

    public User(String data) throws InvalidDataException {
        String[] parts = data.split(" ");
        if (parts.length != 6) {
            throw new InvalidDataException("Invalid number of fields");
        }

        this.lastName = parts[0];
        this.firstName = parts[1];
        this.middleName = parts[2];

        try {
            this.dateOfBirth = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            throw new InvalidDataException("Invalid date format");
        }

        try {
            Long.parseLong(parts[4]);
            this.phoneNumber = parts[4];
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Invalid phone number format");
        }

        if (parts[5].length() != 1 || (!parts[5].equalsIgnoreCase("f") && !parts[5].equalsIgnoreCase("m"))) {
            throw new InvalidDataException("Invalid gender");
        }
        this.gender = parts[5].charAt(0);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " " +
                dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
                phoneNumber + " " + gender;
    }
}

public class HW3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные пользователя:");
        String userData = scanner.nextLine();

        try {
            User user = new User(userData);
            saveUserToFile(user);
            System.out.println("Данные успешно сохранены.");
        } catch (InvalidDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    private static void saveUserToFile(User user) throws IOException {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(user.getLastName() + ".txt", true);
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(user.toString());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
} 