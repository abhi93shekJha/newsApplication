package com.gsatechworld.gugrify.view.dashboard;

import java.util.ArrayList;

/**
 * Created by Vithin on 02/08/2018.
 */

public class SectionDataModel {
    private String headerTitle;
    private ArrayList<SingleItemModel> allItemInSection;

    public SectionDataModel() {
    }

    public SectionDataModel(String headerTitle, ArrayList<SingleItemModel> allItemInSection) {
        this.headerTitle = headerTitle;
        this.allItemInSection = allItemInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel> getAllItemInSection() {
        return allItemInSection;
    }

    public void setAllItemInSection(ArrayList<SingleItemModel> allItemInSection) {
        this.allItemInSection = allItemInSection;
    }
}
