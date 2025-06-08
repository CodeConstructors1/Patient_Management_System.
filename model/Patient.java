package model;

public class Patient {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private String disease;

    public enum Gender {
        MALE, FEMALE, OTHER;

        public static Gender fromString(String genderStr) {
            if (genderStr == null) {
                throw new IllegalArgumentException("Gender cannot be null");
            }
            switch (genderStr.toUpperCase()) {
                case "MALE":
                    return MALE;
                case "FEMALE":
                    return FEMALE;
                case "OTHER":
                    return OTHER;
                default:
                    throw new IllegalArgumentException("Invalid gender: " + genderStr);
            }
        }
    }

    // Constructor without ID (for new patients)
    public Patient(String name, int age, Gender gender, String disease) {
        validate(name, age, disease);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    // Constructor with ID (for existing patients)
    public Patient(int id, String name, int age, Gender gender, String disease) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        validate(name, age, disease);
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    // Validation method - Improved with regex and range checks
    private void validate(String name, int age, String disease) {
        if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-Z .'-]+")) {
            throw new IllegalArgumentException("Name must contain only letters, spaces, periods, apostrophes or hyphens");
        }
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
        if (disease == null || disease.trim().isEmpty() || !disease.matches("[a-zA-Z0-9 .'-]+")) {
            throw new IllegalArgumentException("Disease must contain only letters, digits, spaces, periods, apostrophes or hyphens");
        }
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || !name.matches("[a-zA-Z .'-]+"))
            throw new IllegalArgumentException("Invalid name format.");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 150)
            throw new IllegalArgumentException("Age must be between 0 and 150.");
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (gender == null)
            throw new IllegalArgumentException("Gender cannot be null.");
        this.gender = gender;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        if (disease == null || !disease.matches("[a-zA-Z0-9 .'-]+"))
            throw new IllegalArgumentException("Invalid disease format.");
        this.disease = disease;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Age: %d, Gender: %s, Disease: %s",
                id, name, age, gender, disease);
    }
}
