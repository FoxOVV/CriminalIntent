package com.example.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;


    public static CrimeLab get(Context context) {
        //Синглетный класс - можно создать только 1 экзмепляр класса
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        //Создает массив из 100 экземпляров класса Crime
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2==0); //Для каждого второго объекта
            if (i%5==0) {
                crime.setRequiresPolice(0); //Для каждого пятого объекта
            }else {
                crime.setRequiresPolice(1);
            }
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrime() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime: mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
