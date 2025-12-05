package com.example.zepto;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {

    private final List<String> steps;
    private final int currentStepIndex;

    public TrackingAdapter(List<String> steps, int currentStepIndex) {
        this.steps = steps;
        this.currentStepIndex = currentStepIndex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tracking_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.statusLabel.setText(steps.get(position));

        if (position <= currentStepIndex) {
            holder.dot.setBackgroundResource(R.drawable.circle_shape_green);

            holder.line.setBackgroundColor(Color.parseColor("#00FF00"));
        } else {
            holder.dot.setBackgroundResource(R.drawable.circle_shape_grey);
            holder.line.setBackgroundColor(Color.GRAY);
        }
        if (position == steps.size() - 1) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
    // Inner ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView statusLabel;
        View dot;
        View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusLabel = itemView.findViewById(R.id.tv_status_label);
            dot = itemView.findViewById(R.id.view_status_dot);
            line = itemView.findViewById(R.id.view_status_line);
        }
    }
}
