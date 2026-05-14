package com.mikael.eCommerce.errors;

import java.util.Map;

public record ValidationErrorResponse(int statusCode, Map<String, String> errors) {
}
