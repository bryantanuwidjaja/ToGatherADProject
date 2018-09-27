package com.example.bryan.togatheradproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PromotionList extends ArrayAdapter {
    private android.app.Activity context;
    private List<Promotion> promotionList;

    public PromotionList(Activity context, List<Promotion> promotionList) {
        super(context, R.layout.promotion_layout, promotionList);
        this.context = context;
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.promotion_layout, null, true);
        ImageView imageView_promotionImage = listViewItem.findViewById(R.id.imageView_PromotionLayout_promotionImage);
        TextView textView_description = listViewItem.findViewById(R.id.textView_PromotionLayout_Promotion);

        Promotion promotion = promotionList.get(position);
        textView_description.setText(promotion.getDetail());
        imageView_promotionImage.setImageResource(promotion.getDrawable());

        return listViewItem;
    }
}
