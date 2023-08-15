package ru.springBoot.Playlist.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class AuthorDTO {
    @Length(min = 2, max = 200 , message = "название группы должно быть от 2 до 200 символов")
    private String name;

    @Max(value = 2023, message = "год создание группы не должен быть больше 2024 года")
    @Min(value = 1, message = "год группы больше 0")
    private int age;

    @Email(message = "введенный email не валидный")
    private String email;

    public AuthorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
