package android.bignerdranch.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager2 mViewPager;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_crime_pager);

        mViewPager = (ViewPager2)findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Part of what I changed for Assignment 9, updating from viewpager to viewpager2
        // added in fragmentstateadapter and added in the lifecycle as a parameter
        // Following "Migrate from ViewPager to ViewPager2" on developer.android.com
        mViewPager.setAdapter(new FragmentStateAdapter(fragmentManager, getLifecycle()) {
            @Override
            public int getItemCount() { // also added in getItemCount instead of getCount
                return mCrimes.size();
            }


            @Override
            public Fragment createFragment(int position) { // and added in createFragment instead of getItem
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getID());
            }
/*
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getID());
            }

            @Override
            public int getCount() {

                return mCrimes.size();
            }
*/

        });

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        for(int i = 0; i < mCrimes.size(); i++) {
            if(mCrimes.get(i).getID().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(
            Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext,
                CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
