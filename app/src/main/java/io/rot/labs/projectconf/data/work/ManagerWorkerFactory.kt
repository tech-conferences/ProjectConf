package io.rot.labs.projectconf.data.work

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
        addFactory(EventWorkerFactory(eventsRepository, userTopicPreferences))
    }
}
