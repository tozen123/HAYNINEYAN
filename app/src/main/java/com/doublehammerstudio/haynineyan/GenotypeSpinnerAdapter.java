package com.doublehammerstudio.haynineyan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doublehammerstudio.haynineyan.R;

public class GenotypeSpinnerAdapter extends BaseAdapter {

    private Context context;
    private String[] genotypes;
    private int[] images; // Corresponding images for each genotype

    public GenotypeSpinnerAdapter(Context context, String[] genotypes, int[] images) {
        this.context = context;
        this.genotypes = genotypes;
        this.images = images;
    }

    @Override
    public int getCount() {
        return genotypes.length;
    }

    @Override
    public Object getItem(int position) {
        return genotypes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_with_image, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.spinner_item_image);
        TextView textView = convertView.findViewById(R.id.spinner_item_text);

        // Set image and text
        imageView.setImageResource(images[position]);
        textView.setText(genotypes[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
