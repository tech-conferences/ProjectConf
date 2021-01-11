/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.archive

import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseItemViewModel
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Inject

class ArchiveItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseItemViewModel<Int>(schedulerProvider, compositeDisposable, networkDBHelper) {

    override fun onCreate() {}
}
