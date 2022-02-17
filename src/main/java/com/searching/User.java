package com.searching;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class User {

    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String middlename;
    @NonNull
    private String job;
    @NonNull
    private String img;
    @NonNull
    private String gender;
    @NonNull
    private String address;
    @NonNull
    private String phone;
    @NonNull
    private LocalDate birthdate;
    @NonNull
    private String birthplace;
    @NonNull
    private String nation;
    @NonNull
    private List<Skill> skillList;
    private List<Experience> experienceList = new LinkedList<>();
    private List<Experience> educationList = new LinkedList<>();
}
