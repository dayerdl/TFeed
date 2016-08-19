package com.raza.twitter.feed.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jaffarraza on 19/08/16.
 */
public class VideoInfo implements Serializable {
    /**
     * The aspect ratio of the video, as a simplified fraction of width and height in a 2-element
     * list. Typical values are [4, 3] or [16, 9].
     */
    @SerializedName("aspect_ratio")
    public final List<Integer> aspectRatio;

    /**
     * The length of the video, in milliseconds.
     */
    @SerializedName("duration_millis")
    public final long durationMillis;

    /**
     * Different encodings/streams of the video.
     */
    @SerializedName("variants")
    public final List<Variant> variants;

    public VideoInfo(List<Integer> aspectRatio, long durationMillis, List<Variant> variants) {
        this.aspectRatio = aspectRatio;
        this.durationMillis = durationMillis;
        this.variants = variants;
    }

    public static class Variant implements Serializable {
        @SerializedName("bitrate")
        public final long bitrate;

        @SerializedName("content_type")
        public final String contentType;

        @SerializedName("url")
        public final String url;

        public Variant(long bitrate, String contentType, String url) {
            this.bitrate = bitrate;
            this.contentType = contentType;
            this.url = url;
        }
    }
}

