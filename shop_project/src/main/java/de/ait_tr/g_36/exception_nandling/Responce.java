package de.ait_tr.g_36.exception_nandling;

import java.util.Objects;

public class Responce {

    private String message;

    public Responce(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Responce responce)) return false;
        return Objects.equals(message, responce.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }

    public String toString() {
        return "Response: message - " + message;
    }
}
