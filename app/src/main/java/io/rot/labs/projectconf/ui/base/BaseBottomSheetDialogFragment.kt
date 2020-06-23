package io.rot.labs.projectconf.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.DaggerFragmentComponent
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.di.module.FragmentModule
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel> : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModel: VM

    @LayoutRes
    abstract fun provideLayoutId(): Int

    abstract fun injectDependencies(buildComponent: FragmentComponent)

    abstract fun setupView(savedInstanceState: Bundle?)

    abstract fun setupObservables()

    private fun getBuildComponent() = DaggerFragmentComponent
        .builder()
        .applicationComponent((requireContext().applicationContext as ConfApplication).appComponent)
        .fragmentModule(FragmentModule(this))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(getBuildComponent())
        super.onCreate(savedInstanceState)
        setupObservables()
        viewModel.onCreate()
    }

    override fun getTheme(): Int = R.style.AppModalStyle

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme).apply {

//            this.setContentView(provideLayoutResId())

            setOnShowListener {
                val dialog = it as BottomSheetDialog

                val bottomSheet =
                    dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

                BottomSheetBehavior.from(bottomSheet!!)
                    .setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(provideLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(savedInstanceState)
    }
}
