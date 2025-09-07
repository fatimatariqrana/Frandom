
package com.fat.mah.main.pickImageBase;

import android.net.Uri;

import com.fat.mah.main.base.BaseView;


public interface PickImageView extends BaseView {
    void hideLocalProgress();

    void loadImageToImageView(Uri imageUri);
}
