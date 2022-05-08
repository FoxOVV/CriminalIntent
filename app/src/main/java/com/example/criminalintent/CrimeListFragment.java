package com.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public void onResume() {
        super.onResume();
        updateUI();
    }

    //Создает объект CrimeAdapter и назначает его RecyclerView
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrime();
        if (mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
            //mAdapter.notifyItemChanged(3);
        }
    }

    //Как вягледет ViewHolder -> itemView -> View (это область на экране в которую помещаются все 11 преступлений)
    //Создан чтобы удерживает объект View
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        //Этот конструктор не используется (старая версия)
        //Присвоил в переменные, 2 элемента TextView которые хранятся в list_item_crime
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_data);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        //Присвоил в переменные, 2 элемента TextView которые хранятся в list_item_crime
        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_data);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        //Заполняем TextView данными из класса Crime
        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText( new DateFormat().format("E, d MMM yyyy HH:mm:ss", mCrime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        //Слушатель для нажатия на строку
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    //Активируется, когда требуется создать новый объект ViewHolder
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        // (1) RecyclerView вызывает его самым первым - возвращает количество записей в массиве
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
            View view;

            if (viewType==1) {
                view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.list_item_crime_police, parent, false);
            }

            return new CrimeHolder(view);
        }

        @Override
        // (3) RecyclerView вызывает его третьим, после CrimeHolder - он заполняет объект ViewHolder
        //данными из класса Crime
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime =  mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        //Не обязательный метод, но если он определен он вызывается между 1 и 2 действием
        //Назовем его 1.5, после getItemCount(), но до onCreateViewHolder()
        public int getItemViewType(int position) {
            Crime crime =  mCrimes.get(position);
            return crime.isRequiresPolice();
        }
    }
}
