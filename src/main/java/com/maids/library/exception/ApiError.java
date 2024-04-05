package com.maids.library.exception;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Objects;

public class ApiError {

        private HttpStatus status;
        private String message;
        private List<String> errors;

        public ApiError(){}

        public ApiError(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ApiError apiError)) return false;
            return getStatus() == apiError.getStatus() && Objects.equals(getMessage(), apiError.getMessage()) && Objects.equals(getErrors(), apiError.getErrors());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getStatus(), getMessage(), getErrors());
        }

        @Override
        public String toString() {
            return "ApiError{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", errors=" + errors +
                    '}';
        }
    }