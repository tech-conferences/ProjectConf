/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.changeTheme

import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class ChangeThemeViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {
    override fun onCreate() {}
}
