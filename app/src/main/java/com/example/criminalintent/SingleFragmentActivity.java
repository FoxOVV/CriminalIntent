package com.example.criminalintent;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    //Абстрактный метод класса - обязательно нужно расписать реализацию у классов-потомков
    protected abstract Fragment createFragment();

    @Override
    //Bundle savedInstanceStat - Пакет сохраненное состояние экземпляра
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Установаить activity_fragment.xml как отображение для контента
        setContentView(R.layout.activity_fragment);

        Log.i(TAG,"TAGonCreate");

        //Объявить фрагмент менеджер
        FragmentManager fm = getSupportFragmentManager();

        //Найти фрагмент в фрагмент менеджере где ID фрейма = fragment_container
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //Если в фрейме нет фрагмента, необходимо его обьявить и заполнить
        if (fragment == null) {
            fragment = createFragment();
            //Добавляет созданный фрагмент в фрейм fragment_container, который лежит в xml - activity_fragment
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
