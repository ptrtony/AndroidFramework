package com.sinata.framework.big_gallery;


import android.graphics.Point;

public interface IHiGallery {

    void setHiGalleryInfo(HiGalleryInfo hiGalleryInfo);

    void startAnimationClickChange(Point centerP,int radius);
}
