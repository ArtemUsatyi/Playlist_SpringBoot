package ru.springBoot.Playlist.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Song")
public class Song {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @Length(min = 2, max = 200, message = "название группы должно быть от 2 до 200 символов")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "year_song")
    @Max(value = 2024, message = "год написания песни не должен быть больше 2024 года")
    private int yearSong;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    public Song() {
    }

    public Song(String name, String link, int yearSong) {
        this.name = name;
        this.link = link;
        this.yearSong = yearSong;
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
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getYearSong() {
        return yearSong;
    }

    public void setYearSong(int yearSong) {
        this.yearSong = yearSong;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
