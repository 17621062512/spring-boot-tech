package com.ray.tech.model;

import lombok.Data;

import java.util.Objects;

/**
 * 歌曲
 */
@Data
public class Music {
    /**
     * 歌曲ID
     */
//    @Id
    private String songId;
    /**
     * 媒体资源号
     */
    private String songMid;
    /**
     * 名称
     */
    private String name;
    /**
     * 演唱者
     */
    private String singer;
    /**
     * 连接地址
     */
    private String url;
    /**
     * 专辑
     */
    private String album;
    /**
     * 专辑封面
     */
    private String albumImage;
    /**
     * 专辑ID
     */
    private String albumId;
    /**
     * 间隔
     */
    private long interval;
    /**
     * 数据源
     */
    private Source source;


    private String publishDate;

    private CopyrightStatus copyrightStatus;

    private MatchStatus matchStatus = MatchStatus.MATCH;

    private String matchStatusReason = "风格匹配,可以点播";

    public enum MatchStatus {
        MATCH,
        NOT_MATCH,
        /**
         * music in black
         */
        MIB,
        TOO_LONG,
        LANGUGE_NOT_MATCH,
        NO_COYPRIGHTS
        }

    public enum Source {
        /**
         * QQ音乐
         */
        QQ,
        /**
         * 网易云音乐
         */
        NETEASY,
        KUGOU
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Music)) return false;
        if (!super.equals(o)) return false;
        Music music = (Music) o;
        return Objects.equals(songId, music.songId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(songId);
    }

    public enum CopyrightStatus {
        NORMAL,
        HAS_NO_COPYRIGHT
    }

    public Music() {
    }

    public Music(Music m) {
        this.songId = m.songId;
        this.songMid = m.songMid;
        this.name = m.name;
        this.singer = m.singer;
        this.url = m.url;
        this.album = m.album;
        this.albumImage = m.albumImage;
        this.albumId = m.albumId;
        this.interval = m.interval;
        this.source = m.source;
        this.publishDate = m.publishDate;
        this.copyrightStatus = m.copyrightStatus;
        this.matchStatus = m.matchStatus;
        this.matchStatusReason = m.matchStatusReason;
    }
}
