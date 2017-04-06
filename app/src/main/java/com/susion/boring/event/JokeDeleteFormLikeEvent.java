package com.susion.boring.event;

import com.susion.boring.read.mvp.entity.Joke;

/**
 * Created by susion on 17/3/29.
 */
public class JokeDeleteFormLikeEvent {
    public Joke joke;

    public JokeDeleteFormLikeEvent(Joke data) {
        joke = data;
    }
}
