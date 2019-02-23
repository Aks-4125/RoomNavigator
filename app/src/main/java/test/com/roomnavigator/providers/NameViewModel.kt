package test.com.roomnavigator.providers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import test.com.roomnavigator.db.NameRoomDatabase
import test.com.roomnavigator.entity.Name
import kotlin.coroutines.CoroutineContext

/**
 * View Model to keep a reference to the name repository and
 * an up-to-date list of all names.
 */

class NameViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: NameRepository
    // Using LiveData and caching what getAlphabetizednames returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allnames: LiveData<List<Name>>

    init {
        val namesDao = NameRoomDatabase.getDatabase(application, scope).nameDao()
        repository = NameRepository(namesDao)
        allnames = repository.allNames
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(name: Name) = scope.launch(Dispatchers.IO) {
        repository.insert(name)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
