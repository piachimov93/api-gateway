package com.piachimov.apigateway.exception;

public record ErrorResponseDTO(String errorMessage, int errorCode) {
}
