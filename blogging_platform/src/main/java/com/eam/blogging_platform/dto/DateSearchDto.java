package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class DateSearchDto {

    @Min(value = 2024, message = "Year must be greater than or equal to 2024")
    @Max(value = 3000, message = "Year must be 3000 at maximum")
    private int year;

    @Min(value = 1, message = "Month must be greater than or equal to 1")
    @Max(value = 12, message = "Month must be 12 at maximum")
    private int month;

    @Min(value = 1, message = "Month must be greater than or equal to 1")
    @Max(value = 31, message = "Month must be 31 at maximum")
    private int day;

    //Getters and setters

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
