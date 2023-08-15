package ru.springBoot.Playlist.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SongDTO {
    @Length(min = 2, max = 200, message = "название группы должно быть от 2 до 200 символов")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "year_song")
    @Max(value = 2024, message = "год написания песни не должен быть больше 2024 года")
    @Min(value = 900, message = "год написания песни не должен быть не меньше 900 года")
    private int yearSong;

    public SongDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
