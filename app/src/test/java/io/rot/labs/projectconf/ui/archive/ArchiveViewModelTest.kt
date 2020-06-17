package io.rot.labs.projectconf.ui.archive

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArchiveViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var archiveYearObserver: Observer<List<Int>>

    lateinit var archiveViewModel: ArchiveViewModel

    @Before
    fun setup() {
        archiveViewModel = ArchiveViewModel(
            TestSchedulerProvider(TestScheduler()),
            CompositeDisposable(),
            networkDBHelper
        )

        archiveViewModel.archiveYears.observeForever(archiveYearObserver)
    }

    @Test
    fun getArchiveYearsTest() {
        val yearList = TimeDateUtils.getConfYearsList()
        val archive = yearList.subList(0, yearList.lastIndex).reversed()

        archiveViewModel.onCreate()

        verify(archiveYearObserver).onChanged(archive)
    }

    @After
    fun tearDown() {
        archiveViewModel.archiveYears.removeObserver(archiveYearObserver)
    }
}
