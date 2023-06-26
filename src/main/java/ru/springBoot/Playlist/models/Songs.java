package ru.springBoot.Playlist.models;

import javax.persistence.*;

@Entity
@Table(name = "Song")
public class Songs {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "year_song")
    private int yearSong;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Authors author;

    public Songs() {
    }

    public Songs(String name, String link, int yearSong) {
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

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }
}
