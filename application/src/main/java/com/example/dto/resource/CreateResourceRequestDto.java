package com.example.dto.resource;

import com.example.constraint.ResourceTypeConstraint;
import com.example.dto.content.CreateContentRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateResourceRequestDto {

    @NotBlank
    @Size(max = 30, message = "Resource name length should not exceed 30 characters")
    private String name;

    @Size(max = 30, message = "Author name length should not exceed 30 characters")
    private String author;

    @ResourceTypeConstraint
    private String resourceType;

    @Valid
    private List<CreateContentRequestDto> contents;

    @Pattern(regexp = "^\\d{1,19}$", message = "Subject id must be long positive number")
    private String subject;
}
