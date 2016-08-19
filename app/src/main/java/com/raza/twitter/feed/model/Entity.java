package com.raza.twitter.feed.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jaffarraza on 19/08/16.
 */
class Entity implements Serializable {
    private static final int START_INDEX = 0;
    private static final int END_INDEX = 1;

    /**
     * An array of integers indicating the offsets.
     */
    @SerializedName("indices")
    public final List<Integer> indices;

    public Entity(int start, int end) {
        final List<Integer> temp = new ArrayList<>(2);
        temp.add(START_INDEX, start);
        temp.add(END_INDEX, end);

        indices = Collections.unmodifiableList(temp);
    }

    public int getStart() {
        return indices.get(START_INDEX);
    }

    public int getEnd() {
        return indices.get(END_INDEX);
    }
}

