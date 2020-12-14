package com.example.miis200;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChoiceimageAdapter extends RecyclerView.Adapter<ChoiceimageAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<ChoiceimageItemrecycler> choiceimageItemrecyclers;

    public ChoiceimageAdapter(Context context, ArrayList<ChoiceimageItemrecycler> choiceimageItemrecyclers) {
        this.context = context;
        this.choiceimageItemrecyclers = choiceimageItemrecyclers;
    }

    public void setChoiceimageItemrecyclers(ArrayList<ChoiceimageItemrecycler> choiceimageItemrecyclers) {
        this.choiceimageItemrecyclers = new ArrayList<>();
        this.choiceimageItemrecyclers = choiceimageItemrecyclers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choiceimage, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(choiceimageItemrecyclers.get(position));
    }

    @Override
    public int getItemCount() {
        return choiceimageItemrecyclers.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView,eyeimage;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            eyeimage = itemView.findViewById(R.id.eyeimage);

        }

        void bind(final ChoiceimageItemrecycler choiceimageItemrecycler) {
            imageView.setVisibility(choiceimageItemrecycler.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(choiceimageItemrecycler.getName());
            eyeimage.setImageBitmap(choiceimageItemrecycler.getEyeimage());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choiceimageItemrecycler.setChecked(!choiceimageItemrecycler.isChecked());
                    imageView.setVisibility(choiceimageItemrecycler.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<ChoiceimageItemrecycler> getAll() {
        return choiceimageItemrecyclers;
    }

    public ArrayList<ChoiceimageItemrecycler> getSelected() {
        ArrayList<ChoiceimageItemrecycler> selected = new ArrayList<>();
        for (int i = 0; i < choiceimageItemrecyclers.size(); i++) {
            if (choiceimageItemrecyclers.get(i).isChecked()) {
                selected.add(choiceimageItemrecyclers.get(i));
            }
        }
        return selected;
    }
}