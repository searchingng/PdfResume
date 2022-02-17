package com.searching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class Experience {
    private String position;
    private String describtion;
    private LocalDate startDate;
    private LocalDate endDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getStart(){
        return startDate.format(formatter);
    }

    public String getEnd(){
        return endDate.format(formatter);
    }

    public void setStart(String date){
        startDate = LocalDate.parse(date, formatter);
    }

    public void setEnd(String date){
        endDate = LocalDate.parse(date, formatter);
    }
}
