package io.rot.labs.projectconf.ui.bookmarks

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment

class BookmarksFragment : BaseFragment<BookmarksViewModel>() {

    companion object {
        const val TAG = "BookmarksFragment"
        fun newInstance(): BookmarksFragment {
            val args = Bundle()
            val fragment = BookmarksFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

    override fun provideLayoutId(): Int = R.layout.fragment_bookmarks
}
