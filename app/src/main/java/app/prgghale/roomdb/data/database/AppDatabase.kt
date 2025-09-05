package app.prgghale.roomdb.data.database

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private val DbName = "RoomDbName1111"

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
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabaseBuilder(context)
                    .setDriver(BundledSQLiteDriver())//TODO KMP Callback is SQLiteConnection  Android is SupportSQLiteDatabase
                    .addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(connection: SQLiteConnection) {
                            super.onCreate(connection)
                            Log.i("AppDatabase", "Room.Callback.onCreate: Database structure created. connection: $connection")
                            INSTANCE?.let { database ->
                                scope.launch {
                                    val list=listOf(
                                        ProfessionTable(professionName = "Ui/Ux Designer"),
                                        ProfessionTable(professionName = "Android Developer"),
                                        ProfessionTable(professionName = "Node Developer"),
                                        ProfessionTable(professionName = "React Developer"),
                                        ProfessionTable(professionName = "Laravel Developer"),
                                        ProfessionTable(professionName = "Django Developer"),
                                        ProfessionTable(professionName = "QA Engineer")
                                    )
                                    try {
                                        database.getProfessionDao().insert(list)
                                    }catch (e: Exception) {
                                        Log.e("AppDatabaseCallback", "Error populating professions: ${e.message}", e)
                                    }
                                }
                            }
                        }

                        override fun onOpen(connection: SQLiteConnection) {
                            super.onOpen(connection)
                            Log.d("AppDatabase", "Room.Callback.onOpen: Database opened. connection: $connection")
                        }
                    })
                    .fallbackToDestructiveMigrationOnDowngrade(true)
                    .setQueryCoroutineContext(Dispatchers.IO)
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
        private fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
            val appContext = ctx.applicationContext
            return Room.databaseBuilder<AppDatabase>(
                context = appContext,
                name = DbName
            )
        }
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