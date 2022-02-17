package com.searching;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Skill java = new Skill("Java", 5);
        Skill sql = new Skill("PostgreSQL", 5);
        Skill bot = new Skill("Telegram Bot", 4);
        Skill jdbc = new Skill("JDBC", 5);
        Skill spring = new Skill("Spring", 4);
        Skill front = new Skill("Frontend", 3);
        List<Skill> skills = List.of(java, sql, bot, jdbc, spring, front);

        Experience e1 = Experience.builder()
                .position("Mentor")
                .describtion("PDP da 1 yil mentor bo'lib ishlalsdasdas....")
                .startDate(LocalDate.of(2018, 9, 15))
                .endDate(LocalDate.of(2020, 4, 24))
                .build();

        Experience e2 = Experience.builder()
                .position("Backend Dasturchi")
                .describtion("... Bank da programmis bo'lib ... Lorem ipsum ...")
                .startDate(LocalDate.of(2020, 7, 11))
                .endDate(LocalDate.of(2021, 8, 2))
                .build();

        List<Experience> experienceList = List.of(e1, e2);

        Experience edu = Experience.builder()
                .position("Java Backend Certificate")
                .describtion("PDP da 6.541 oy ta'lim olganman....")
                .startDate(LocalDate.of(2021, 10, 15))
                .endDate(LocalDate.of(2022, 4, 28))
                .build();

        Experience edu2 = Experience.builder()
                .position("C++ olimpiadachi")
                .describtion("Maktabda 4 yil tayyorlangaman...")
                .startDate(LocalDate.of(2018, 9, 1))
                .endDate(LocalDate.of(2021, 4, 28))
                .build();

        List<Experience> educationsList = List.of(edu, edu2);


        User user = User.builder()
                .name("Sirojiddin")
                .surname("Kilichbaev")
                .middlename("Odilbek o'g'li")
                .job("Java Backend Developer")
                .gender("Male")
                .img("curr.png")
                .address("Tashkent city, Yunusobod region, Bog'ishamol street 17.")
                .phone("+998994246558")
                .birthdate(LocalDate.of(2004, 5, 1))
                .birthplace("Khorezm")
                .nation("Uzbek")
                .skillList(skills)
                .experienceList(experienceList)
                .educationList(educationsList)
                .build();

        try {
            new PdfUtil().create(user, "resume.pdf");
        } catch (IOException e){

        }
    }
}
