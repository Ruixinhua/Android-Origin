package com.origin.rxh.origin.general;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.origin.rxh.origin.R;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {
    private String[]itemNumbers;

    public StageAdapter(String[] itemNumbers) {
        this.itemNumbers = itemNumbers;
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView itemText;

        public StageViewHolder(@NonNull View itemView, TextView itemText) {
            super(itemView);
            this.itemText = itemText;
        }
    }
    @NonNull
    @Override
    public StageAdapter.StageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_text, viewGroup, false);
        TextView itemText = v.findViewById(R.id.item_text);
        return new StageViewHolder(v, itemText);
    }

    @Override
    public void onBindViewHolder(@NonNull StageViewHolder stageViewHolder, int i) {
        stageViewHolder.itemText.setText(itemNumbers[i]);
    }

    @Override
    public int getItemCount() {
        return itemNumbers.length;
    }

}
