package test.com.roomnavigator.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import test.com.roomnavigator.entity.Name

@Dao
interface NameDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from name_table ORDER BY name ASC")
    fun getAlphabetizedNames(): LiveData<List<Name>>

    // We do not need a conflict strategy, because the name is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert
    fun insert(name: Name)

    @Query("DELETE FROM name_table")
    fun deleteAll()
}