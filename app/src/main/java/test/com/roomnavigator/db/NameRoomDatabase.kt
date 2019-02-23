package test.com.roomnavigator.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.com.roomnavigator.dao.NameDao
import test.com.roomnavigator.entity.Name

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Name::class], version = 1)
abstract class NameRoomDatabase : RoomDatabase() {

    abstract fun nameDao(): NameDao

    companion object {
        @Volatile
        private var INSTANCE: NameRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NameRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NameRoomDatabase::class.java,
                    "name_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(NameDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class NameDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.nameDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more names, just add them.
         */
        fun populateDatabase(nameDao: NameDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            nameDao.deleteAll()
            nameDao.insert(Name("Android"))
            nameDao.insert(Name("Enthusiastic"))
        }
    }

}