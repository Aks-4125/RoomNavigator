package test.com.roomnavigator.providers

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import test.com.roomnavigator.dao.NameDao
import test.com.roomnavigator.entity.Name


/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class NameRepository(private val nameDao: NameDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNames: LiveData<List<Name>> = nameDao.getAlphabetizedNames()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(name: Name) {
        nameDao.insert(name)
    }
}
