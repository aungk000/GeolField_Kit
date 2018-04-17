package me.aungkooo.geologist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.adapter.ViewPagerAdapter;


public class FieldNoteFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_field_note, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_field_note);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_field_note);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        Fragment[] fragments = {new MyNotesFragment(), new ExploreOthersFragment()};
        Collections.addAll(fragmentList, fragments);

        ArrayList<String> titleList = new ArrayList<>();
        String[] titles = {"My Notes", "Explore Others"};
        Collections.addAll(titleList, titles);

        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager(), fragmentList, titleList));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
