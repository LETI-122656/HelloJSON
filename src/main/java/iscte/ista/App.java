package iscte.ista;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class App {

    private static final String FIRSTNAMES_F_FILE = "data/firstnames_f.json";
    private static final String FIRSTNAMES_M_FILE = "data/firstnames_m.json";
    private static final String SURNAMES_FILE = "data/surnames.json";
    private static final String STUDENTS_CVS_FILE = "data/students.cvs";

    private static final int INITIAL_NUMBER = 21000;

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        // Ler os nomes dos ficheiros JSON
        String[] femaleNames = mapper.readValue(new File(FIRSTNAMES_F_FILE), String[].class);
        String[] maleNames = mapper.readValue(new File(FIRSTNAMES_M_FILE), String[].class);
        String[] surnames = mapper.readValue(new File(SURNAMES_FILE), String[].class);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Quantos estudantes pretende gerar? ");
        int numberOfStudents = scanner.nextInt();

        Random random = new Random();
        Set<String> usedNames = new HashSet<>();

        try (FileWriter writer = new FileWriter(STUDENTS_CVS_FILE)) {
            for (int i = 0; i < numberOfStudents; i++) {
                String fullName;
                do {
                    String firstName;

                    if (random.nextBoolean()) {
                        firstName = femaleNames[random.nextInt(femaleNames.length)];
                    } else {
                        firstName = maleNames[random.nextInt(maleNames.length)];
                    }

                    String surname =
                            surnames[random.nextInt(surnames.length)];

                    fullName = firstName + " " + surname;

                } while (usedNames.contains(fullName));

                usedNames.add(fullName);

                Student student = new Student(fullName, INITIAL_NUMBER + i);

                writer.write(student.toCSV());
                writer.write(System.lineSeparator());
            }
        }

        System.out.println(
                numberOfStudents + " estudantes foram criados em "
                        + STUDENTS_CVS_FILE
        );
    }
}