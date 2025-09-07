
package com.fat.mah.main.editProfile;

import com.fat.mah.main.pickImageBase.PickImageView;


public interface EditProfileView extends PickImageView {
    void setName(String username);

    void setProfilePhoto(String photoUrl);

    String getNameText();

    void setNameError(String string);
}
