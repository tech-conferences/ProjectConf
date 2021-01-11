/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.background.work

import androidx.work.DelegatingWorkerFactory
import io.rot.labs.projectconf.data.local.prefs.UserTopicPreferences
import io.rot.labs.projectconf.data.repository.EventsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManagerWorkerFactory @Inject constructor(
    eventsRepository: EventsRepository,
    userTopicPreferences: UserTopicPreferences
) :
    DelegatingWorkerFactory() {
    init {
        addFactory(
            EventWorkerFactory(
                eventsRepository,
                userTopicPreferences
            )
        )
    }
}
