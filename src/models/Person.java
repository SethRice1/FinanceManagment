package models;

/**
 * Person - Abstract Base Class representing a general person with basic details.
 */
public abstract class Person {
    private String name;
    private String email;

    /**
     * Constructor to initialize the name and email of the person.
     * 
     * @param name  the name of the person
     * @param email the email address of the person
     */
    public Person(String name, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.name = name;
        this.email = email;
    }

    /**
     * Abstract method to display the role of the person.
     * This method must be implemented by all subclasses.
     */
    public abstract void displayRole();

    /**
     * Gets the name of the person.
     * 
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     * 
     * @param name the new name to set
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Gets the email address of the person.
     * 
     * @return the email address of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the person.
     * 
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }
}