package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Music", schema = "dbo", catalog = "ladybg")
public class Music implements Serializable {
    private int songId;
    private String songName;
    private String artistName;
    private String albumName;
    private String genreType;

    public Music() {
    }

    public Music(int songId, String songName, String artistName, String albumName, String genreType) {
        this.songId = songId;
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.genreType = genreType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id", nullable = false)
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Basic
    @Column(name = "song_name", nullable = false, length = 25)
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Basic
    @Column(name = "artist_name", nullable = false, length = 25)
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Basic
    @Column(name = "album_name", nullable = true, length = 25)
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Basic
    @Column(name = "genre_type", nullable = false, length = 25)
    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return songId == music.songId &&
                Objects.equals(songName, music.songName) &&
                Objects.equals(artistName, music.artistName) &&
                Objects.equals(albumName, music.albumName) &&
                Objects.equals(genreType, music.genreType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, songName, artistName, albumName, genreType);
    }
}
