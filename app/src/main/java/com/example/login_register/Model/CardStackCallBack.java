package com.example.test_swip_page;

import androidx.recyclerview.widget.DiffUtil;

import com.example.login_register.Model.ItemModel;

import java.util.List;
import java.util.ListIterator;

public class CardStackCallBack extends DiffUtil.Callback {
    private List<ItemModel> old,baru;

    public CardStackCallBack(List<ItemModel> old, List<ItemModel> baru) {
        this.old = old;
        this.baru = baru;
    }

    public CardStackCallBack() {
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}
