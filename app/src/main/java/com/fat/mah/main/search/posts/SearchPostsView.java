
package com.fat.mah.main.search.posts;

import com.fat.mah.main.base.BaseFragmentView;
import com.fat.mah.Post;

import java.util.List;


public interface SearchPostsView extends BaseFragmentView {
    void onSearchResultsReady(List<Post> posts);
    void showLocalProgress();
    void hideLocalProgress();
    void showEmptyListLayout();
}
