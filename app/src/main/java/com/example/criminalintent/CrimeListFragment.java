package com.example.criminalintent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,"TAGonCreateView");

        //Используем fragment_crime_list.xml для наполнения данными
        View view = inflater.inflate(R.layout.fragment_crime_list, container,false);
        //Нашли необходимый визуальный виджет (типо div, только специальный - androidx.recyclerview.widget.RecyclerView)
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //Устанавливаем менеджер компановки - в нашем случае вертикальная линия (колонка) - LinearLayoutManager
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }
    //Создает объект CrimeAdapter и назначает его RecyclerView
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrime();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    //Как вягледет ViewHolder -> itemView -> View (это область на экране в которую помещаются все 11 преступлений)
    //Создан чтобы удерживает объект View
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        //Присвоил в переменные, 2 элемента TextView которые хранятся в list_item_crime
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_data);
        }

        //Заполняем TextView данными из класса Crime
        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(),mCrime.getTitle() + " checked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //Активируется, когда требуется создать новый объект ViewHolder
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        // (1) RecyclerView вызывает его самым первым - вохвращает количество записей в массиве
        public int getItemCount() {
            return mCrimes.size();
        }

        @NonNull
        @Override
        // (2) RecyclerView вызывает его вторым, после getItemCount - он создает объект ViewHolder
        //Вызывается, когда ему требуется новое представление для отображения элемента
        //Другими словами вызывается 11 раз - столько записей одновременно помещается на экран смартфона
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(TAG,"TAGonCreateViewHolder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        // (3) RecyclerView вызывает его третьим, после CrimeHolder - он заполняет объект ViewHolder
        //данными из класса Crime
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime =  mCrimes.get(position);
            holder.bind(crime);
        }
    }
}
