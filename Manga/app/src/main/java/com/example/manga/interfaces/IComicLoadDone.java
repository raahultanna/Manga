package com.example.manga.interfaces;

import com.example.manga.model.Comic;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Comic> comics);
}

