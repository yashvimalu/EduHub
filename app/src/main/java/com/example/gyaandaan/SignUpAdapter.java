package com.example.gyaandaan;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SignUpAdapter extends FragmentStateAdapter {
    private SignUpAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==1){
                return new TeacherFragment();
        }
        return new Student_Fragment_2();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}