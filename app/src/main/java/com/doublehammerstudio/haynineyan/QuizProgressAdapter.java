package com.doublehammerstudio.haynineyan;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizProgressAdapter extends RecyclerView.Adapter<QuizProgressAdapter.ViewHolder> {

    private List<String> quizProgressList;

    public QuizProgressAdapter(List<String> quizProgressList) {
        this.quizProgressList = quizProgressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String progress = quizProgressList.get(position);

        // Split the stored string into its components based on the structure
        String[] parts = progress.split(", ");
        for (String part : parts) {
            if (part.startsWith("Topic:")) {
                String value = part.split(":")[1].trim();
                String displayText;
                if (value.equals("Non Mendelian")) {
                    displayText = "Non-Mendelian Inheritance";
                } else if (value.equals("DNA")) {
                    displayText = "DNA";
                } else if (value.equals("Sex-Related Inheritance")) {
                    displayText = "Sex-Related Inheritance";
                } else {
                    displayText = value;
                }
                holder.tvTopic.setText(createBoldText("Topic: ", displayText));

            } else if (part.startsWith("Difficulty:")) {
                String value = part.split(":")[1].trim();
                holder.tvDifficulty.setText(createBoldText("Difficulty: ", value));

            } else if (part.startsWith("Score:")) {
                String value = part.split(":")[1].trim();
                holder.tvScore.setText(createBoldText("Score: ", value));

            } else if (part.startsWith("Percentage:")) {
                String value = part.split(":")[1].trim();
                holder.tvPercentage.setText(createBoldText("Percentage: ", value));

                // Set color based on percentage
                double percentage = Double.parseDouble(value.replace("%", ""));
                if (percentage < 30) {
                    holder.tvScore.setTextColor(Color.RED);
                } else if (percentage < 50) {
                    holder.tvScore.setTextColor(Color.parseColor("#FFA500"));
                } else if (percentage >= 80) {
                    holder.tvScore.setTextColor(Color.GREEN);
                }
            } else if (part.startsWith("Date:")) {
                String value = part.split(":")[1].trim();
                holder.tvDate.setText(createBoldText("Date: ", value));
            }
        }
    }
    private SpannableString createBoldText(String label, String value) {
        SpannableString spannable = new SpannableString(label + value);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), label.length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
    @Override
    public int getItemCount() {
        return quizProgressList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvDifficulty, tvScore, tvPercentage, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTopic = itemView.findViewById(R.id.tv_topic);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            tvScore = itemView.findViewById(R.id.tv_score);
            tvPercentage = itemView.findViewById(R.id.tv_percentage);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
