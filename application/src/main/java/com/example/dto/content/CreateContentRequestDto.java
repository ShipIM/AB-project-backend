package com.example.dto.content;

import com.example.constraint.ContentTypeConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateContentRequestDto {

    @NotBlank
    @Size(max = 30, message = "Filename length should not exceed 30 characters")
    private String filename;

    private byte[] bytes;

    @ContentTypeConstraint(message = "Unknown content type")
    private String contentType;

    @Pattern(regexp = "^\\d{1,19}$", message = "Content size must be long positive number")
    private String size;

}
