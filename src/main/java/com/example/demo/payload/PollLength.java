package com.example.demo.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PollLength {
    @NotNull
    @Max(23)
    private Integer hours;
    @NotNull
    @Max(7)
    private Integer days;

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
