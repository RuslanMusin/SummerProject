package com.summer.itis.summerproject.ui.cards.add_card_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.model.pojo.opensearch.Item;
import com.summer.itis.summerproject.utils.ImageLoadHelper;


public class AddCardListHolder extends RecyclerView.ViewHolder {

    private static final int MAX_LENGTH = 80;
    private static final String MORE_TEXT = "...";

    private TextView name;
    private TextView description;
    private ImageView imageView;

    @NonNull
    public static AddCardListHolder create(@NonNull Context context) {
        View view = View.inflate(context, R.layout.item_cards, null);
        AddCardListHolder holder = new AddCardListHolder(view);
        return holder;
    }

    public AddCardListHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_name);
        description = itemView.findViewById(R.id.tv_description);
        imageView = itemView.findViewById(R.id.iv_cover);
    }

    public void bind(@NonNull Item item) {
        name.setText(item.getText().getContent());
        String desc = item.getDescription().getContent();
        if (desc != null) {
            description.setText(cutLongDescription(desc));
        } else {
            description.setText(imageView.getContext().getText(R.string.description_default));
        }
        if (item.getImage() != null) {
           /* if(item.getPhotoUrl().equals(String.valueOf(R.drawable.book_default))) {
                ImageLoadHelper.loadPictureByDrawableDefault(imageView,R.drawable.book_default);
            } else {
                ImageLoadHelper.loadPicture(imageView, item.getPhotoUrl());
            }*/
            ImageLoadHelper.loadPicture(imageView, item.getImage().getSource());

        }
    }

    private String cutLongDescription(String description) {
        if (description.length() < MAX_LENGTH) {
            return description;
        } else {
            return description.substring(0, MAX_LENGTH - MORE_TEXT.length()) + MORE_TEXT;
        }
    }
}
