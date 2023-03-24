package org.example.teahouse.core.error;

public class ResourceNotFoundException extends RuntimeException {

    private final String name;

    public ResourceNotFoundException(String name) {
        super("Resource not found: " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
