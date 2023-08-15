package ru.springBoot.Playlist.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @Length(min = 2, max = 200 , message = "название группы должно быть от 2 до 200 символов")
    private String name;
    @Column(name = "age")
    @Max(value = 2023, message = "год создание группы не должен быть больше 2024 года")
    @Min(value = 1, message = "год группы больше 0")
    private int age;
    @Column(name = "email")
    @Email(message = "введенный email не валидный")
    private String email;
    @OneToMany(mappedBy = "author")
    private List<Song> songs;

    public Author() {
    }

    public Author(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", songs=" + songs +
                '}';
    }
}
