package com.gsatechworld.gugrify.model;

public class NavItemModel {
    int navItemImage;
    String navItemName;
    String navItemCount;

    public NavItemModel(int navItemImage, String navItemName, String navItemCount) {
        this.navItemImage = navItemImage;
        this.navItemName = navItemName;
        this.navItemCount = navItemCount;
    }

    public int getNavItemImage() {
        return navItemImage;
    }

    public void setNavItemImage(int navItemImage) {
        this.navItemImage = navItemImage;
    }

    public String getNavItemName() {
        return navItemName;
    }

    public void setNavItemName(String navItemName) {
        this.navItemName = navItemName;
    }

    public String getNavItemCount() {
        return navItemCount;
    }

    public void setNavItemCount(String navItemCount) {
        this.navItemCount = navItemCount;
    }
}
