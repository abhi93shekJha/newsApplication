package com.gsatechworld.gugrify.model;


import com.gsatechworld.gugrify.R;

public class StudentModel implements ViewModel {
    public String name = "Pushan";
    @Override
    public int layoutId() {
        return R.layout.row_student;
    }
}
