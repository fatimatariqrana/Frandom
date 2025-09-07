
package com.fat.mah.main.usersList;

import android.support.annotation.StringRes;

import com.fat.mah.main.base.BaseView;

import java.util.List;

public interface UsersListView extends BaseView {


    void hideLocalProgress();

    void setTitle(@StringRes int title);


    void updateSelectedItem();
}
