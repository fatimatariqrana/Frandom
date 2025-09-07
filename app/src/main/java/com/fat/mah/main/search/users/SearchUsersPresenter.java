/*
 * Copyright 2018 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


package com.fat.mah.main.search.users;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.fat.mah.main.base.BasePresenter;
import com.fat.mah.main.base.BaseView;
import com.fat.mah.ProfileManager;


public class SearchUsersPresenter extends BasePresenter<SearchUsersView> {

    private String currentUserId;
    private Activity activity;
    private ProfileManager profileManager;

    public SearchUsersPresenter(Activity activity) {
        super(activity);
        this.activity = activity;


        currentUserId = FirebaseAuth.getInstance().getUid();
        profileManager = ProfileManager.getInstance(context.getApplicationContext());
    }



    public void loadUsersWithEmptySearch() {
        search("");
    }

    public void search(String searchText) {
        if (checkInternetConnection()) {
            ifViewAttached(SearchUsersView::showLocalProgress);
            profileManager.search(searchText, list -> {
                ifViewAttached(view -> {
                    view.hideLocalProgress();
                    view.onSearchResultsReady(list);

                    if (list.isEmpty()) {
                        view.showEmptyListLayout();
                    }
                });

               // LogUtil.logDebug(TAG, "search text: " + searchText);
                //LogUtil.logDebug(TAG, "found items count: " + list.size());
            });
        } else {
            ifViewAttached(SearchUsersView::hideLocalProgress);
        }
    }

}
