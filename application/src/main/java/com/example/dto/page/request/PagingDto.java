package com.example.dto.page.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingDto {

    @Min(value = 0, message = "Page number must be a positive number")
    @Pattern(regexp = "\\d+", message = "Page size must be a number")
    private String pageNumber;

    @Min(value = 1, message = "Page size must be >= 1")
    @Max(value = 20, message = "Page size must be <= 20")
    @Pattern(regexp = "\\d+", message = "Page size must be a number")
    private String pageSize;

    public PageRequest formPageRequest() {
        return PageRequest.of(
                Integer.parseInt(Optional.ofNullable(pageNumber).orElse("0")),
                Integer.parseInt(Optional.ofNullable(pageSize).orElse("20"))
        );
    }
}
