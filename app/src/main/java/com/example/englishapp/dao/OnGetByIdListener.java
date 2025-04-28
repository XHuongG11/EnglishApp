package com.example.englishapp.dao;

import java.util.List;

public interface OnGetByIdListener <T>{
    void onGetByID(T t);
    void onGetFailed(Exception e);
}
