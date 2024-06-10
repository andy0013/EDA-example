package com.project.core.library.infrastructure.adapters.controllers.output;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(T data, Header header) {
}
