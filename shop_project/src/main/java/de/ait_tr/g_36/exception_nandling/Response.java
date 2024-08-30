package de.ait_tr.g_36.exception_nandling;

import java.util.Objects;

public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response response)) return false;
        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
    @Override
    public String toString() {
        return "Response: message - " + message;
    }
}
