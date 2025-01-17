package com.company.studytool.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    String[] youTubeVideos;
    String[] description;
    String[] questions;
    String[] answers;
    int[] rightAnswer;
    Context context;

    public RecyclerViewAdapter(Context context, String[] youTubeVideos, String[] description) {
        this.youTubeVideos = youTubeVideos;
        this.context = context;
        this.description = description;
    }


    public RecyclerViewAdapter(Context context, String[] questions, String[] answers, int[] rightAnswer) {
        this.rightAnswer = rightAnswer;
        this.answers = answers;
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
