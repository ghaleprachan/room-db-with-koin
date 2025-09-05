package app.prgghale.roomdb.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

 val DbName = "RoomDbName.db"
//Ref :https://developer.android.com/kotlin/multiplatform/room?hl=zh-tw#kotlin-multiplatform_1
@Database(
    entities = [
        UserTable::class,
        ProfessionTable::class
    ],
    version = 1,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        fun getInstance(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
            return builder
                .setDriver(BundledSQLiteDriver())
                .addCallback(object : Callback() {
                    override fun onCreate(connection: SQLiteConnection) {
                        super.onCreate(connection)
                        INSTANCE?.let { database ->
                            scope.launch {
                                val list = listOf(
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
                                } catch (e: Exception) {

                                }
                            }
                        }
                    }

                    override fun onOpen(connection: SQLiteConnection) {
                        super.onOpen(connection)

                    }
                })
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .setQueryCoroutineContext(Dispatchers.IO)
                .addMigrations(MIGRATION_1_2)
                .build().also {
                    INSTANCE = it
                }
        }
    }

    abstract fun getUsersDao(): UsersDao
    abstract fun getProfessionDao(): ProfessionDao

    fun clearDatabase() {
//        clearAllTables()
    }
}

/**
 * Migration(1, 2) means from database version 1 to 2*/
private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SQLiteConnection) {
        // We can perform actions like
        /*database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")*/
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}