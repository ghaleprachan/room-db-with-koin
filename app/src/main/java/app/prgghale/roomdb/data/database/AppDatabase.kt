package app.prgghale.roomdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
private val DbName = "RoomDbName"
@Database(
    entities = [
        UserTable::class,
        ProfessionTable::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            // Classic double-checked locking
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext, scope).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(appContext: Context, scope: CoroutineScope): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DbName)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            scope.launch {
                                database.getProfessionDao().insert(listOf(
                                    ProfessionTable(professionName = "Ui/Ux Designer"),
                                    ProfessionTable(professionName = "Android Developer"),
                                    ProfessionTable(professionName = "Node Developer"),
                                    ProfessionTable(professionName = "React Developer"),
                                    ProfessionTable(professionName = "Laravel Developer"),
                                    ProfessionTable(professionName = "Django Developer"),
                                    ProfessionTable(professionName = "QA Engineer")
                                ))
                            }
                        }
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }

//        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
//            // Classic double-checked locking
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: run {
//                    Log.d("AppDatabase", "Database instance is null, building new one.")
//                    Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java,
//                        DbName
//                    )
//                        .addCallback(object : RoomDatabase.Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                Log.d("AppDatabase", "Room.Callback.onCreate invoked.")
//                                // It's important that INSTANCE is set *before* this callback
//                                // tries to use it to get DAOs, or that the DAOs are accessed
//                                // via the database instance that will be returned by build().
//                                // The current approach of using the INSTANCE from companion object
//                                // relies on .also { INSTANCE = it } setting it just in time.
//                                // A slightly safer way inside the callback if it needed DAOs
//                                // would be to have a reference to the `AppDatabase` being built,
//                                // if the callback was an inner class or had a way to get it.
//                                // However, for this specific structure with .also, it should work.
//
//                                // To avoid potential timing issues with INSTANCE inside the callback,
//                                // it's often more robust for the callback to be an inner class,
//                                // or for the pre-population to be triggered slightly differently.
//                                // But let's stick to your original callback logic.
//                                INSTANCE?.let { database ->
//                                    Log.d("AppDatabase", "INSTANCE is available in onCreate callback.")
//                                    scope.launch {
//                                        Log.d("AppDatabase", "Coroutine launched for pre-populating professions.")
//                                        try {
//                                            val professionsToInsert = listOf(
//                                                ProfessionTable(professionName = "Ui/Ux Designer"),
//                                                ProfessionTable(professionName = "Android Developer"),
//                                                ProfessionTable(professionName = "Node Developer"),
//                                                ProfessionTable(professionName = "React Developer"),
//                                                ProfessionTable(professionName = "Laravel Developer"),
//                                                ProfessionTable(professionName = "Django Developer"),
//                                                ProfessionTable(professionName = "QA Engineer")
//                                            )
//                                            database.getProfessionDao().insert(professionsToInsert)
//                                            Log.d("AppDatabase", "${professionsToInsert.size} professions inserted.")
//                                        } catch (e: Exception) {
//                                            Log.e("AppDatabase", "Error inserting professions: ${e.message}", e)
//                                        }
//                                    }
//                                } ?: run {
//                                    Log.e("AppDatabase", "INSTANCE was null inside Room.Callback.onCreate. Pre-population might fail.")
//                                }
//                            }
//                        })
//                        .addMigrations(MIGRATION_1_2) // Add your migrations
//                        .fallbackToDestructiveMigration() // Use with caution
//                        .build()
//                        .also {
//                            Log.d("AppDatabase", "Database built. Setting INSTANCE.")
//                            INSTANCE = it
//                        }
//                }
//            }
//        }
    }
    abstract fun getUsersDao(): UsersDao
    abstract fun getProfessionDao(): ProfessionDao

    fun clearDatabase() {
        clearAllTables()
    }
}

/**
 * Migration(1, 2) means from database version 1 to 2*/
private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // We can perform actions like
        /*database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")*/
    }
}