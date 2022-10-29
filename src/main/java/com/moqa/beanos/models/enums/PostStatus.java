package com.moqa.beanos.models.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PostStatus {
    @JsonProperty("Public")
    Public,
    @JsonProperty("Private")
    Private,
}
