package com.gsatechworld.gugrify.model;

import java.util.ArrayList;

/**
 * Created by Vithin on 02/08/2018.
 */

public class SectionDataModel {
    private String headerTitle;
    private int img;
    private ArrayList<SingleItemModel> allItemInSection;
    private ArrayList<PlayListItemModel> playListItemModelArrayList;
    private ArrayList<LatestNewItemModel> latestNewItemModelArrayList;
    private ArrayList<OtherNewsItemModel> otherNewsItemModelArrayList;

    public SectionDataModel() {
    }

    public SectionDataModel(String headerTitle, int img) {
        this.headerTitle = headerTitle;
        this.img = img;
    }

    public SectionDataModel(String headerTitle) {
        this.headerTitle = headerTitle;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public ArrayList<SingleItemModel> getAllItemInSection() {
        return allItemInSection;
    }

    public void setAllItemInSection(ArrayList<SingleItemModel> allItemInSection) {
        this.allItemInSection = allItemInSection;
    }

    public ArrayList<PlayListItemModel> getPlayListItemModelArrayList() {
        return playListItemModelArrayList;
    }

    public void setPlayListItemModelArrayList(ArrayList<PlayListItemModel> playListItemModelArrayList) {
        this.playListItemModelArrayList = playListItemModelArrayList;
    }

    public ArrayList<LatestNewItemModel> getLatestNewItemModelArrayList() {
        return latestNewItemModelArrayList;
    }

    public void setLatestNewItemModelArrayList(ArrayList<LatestNewItemModel> latestNewItemModelArrayList) {
        this.latestNewItemModelArrayList = latestNewItemModelArrayList;
    }

    public ArrayList<OtherNewsItemModel> getOtherNewsItemModelArrayList() {
        return otherNewsItemModelArrayList;
    }

    public void setOtherNewsItemModelArrayList(ArrayList<OtherNewsItemModel> otherNewsItemModelArrayList) {
        this.otherNewsItemModelArrayList = otherNewsItemModelArrayList;
    }
}
