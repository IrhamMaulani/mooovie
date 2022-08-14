package com.shiinasoftware.mooovie.ui.shared.utils

import android.content.res.Resources
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faltenreich.skeletonlayout.Skeleton
import com.shiinasoftware.mooovie.R
import com.shiinasoftware.mooovie.models.apis.ApiStatus


fun loadDataStateWithSwipe(
    skeleton: Skeleton,
    swipeRefresh: SwipeRefreshLayout,
    apiStatus: ApiStatus,
    view: View
) = when (apiStatus) {
    ApiStatus.LOADING -> skeleton.showSkeleton()
    ApiStatus.SUCCESS -> {
        skeleton.showOriginal()
        swipeRefresh.isRefreshing = false
    }
    ApiStatus.ERROR -> showLongSnackBar(
        view,
        Resources.getSystem().getString(R.string.failed_to_load_data)
    )
}

fun loadDataState(
    skeleton: Skeleton,
    apiStatus: ApiStatus,
    view: View
) = when (apiStatus) {
    ApiStatus.LOADING -> skeleton.showSkeleton()
    ApiStatus.SUCCESS -> {
        skeleton.showOriginal()
    }
    ApiStatus.ERROR -> showLongSnackBar(
        view,
        Resources.getSystem().getString(R.string.failed_to_load_data)
    )
}
