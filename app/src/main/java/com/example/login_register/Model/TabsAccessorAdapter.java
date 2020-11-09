package com.example.login_register.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabsAccessorAdapter extends FragmentStateAdapter {

    public TabsAccessorAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return  new ChatFragment();
            case 1:
                return  new GroupsFragment();
            case 2:
                return  new ContactsFragment();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
