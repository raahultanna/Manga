package com.example.manga;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manga.adapter.MyChapterAdapter;
import com.example.manga.common.Common;
import com.example.manga.model.Comic;

public class ChapterActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;

    private RecyclerView chapterRecycler;
    private TextView chapterNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        // View
        chapterRecycler = findViewById(R.id.recycler_chapter);
        chapterRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        chapterRecycler.setLayoutManager(layoutManager);
        chapterRecycler.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        chapterNameText = findViewById(R.id.text_chapter_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.comicSelected.Name);

        // Set icon
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetchChapter(Common.comicSelected);
    }

    private void fetchChapter(Comic comicSelected) {
        Common.chapterList = comicSelected.Chapters;
        if (Common.chapterList == null) return;
        chapterRecycler.setAdapter(new MyChapterAdapter(this, comicSelected.Chapters));
        chapterNameText.setText(String.format("CHAPTERS (%d)", comicSelected.Chapters.size()));
    }
}
