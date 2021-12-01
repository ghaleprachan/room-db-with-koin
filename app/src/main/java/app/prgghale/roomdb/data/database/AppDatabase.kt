package app.prgghale.roomdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import app.prgghale.roomdb.data.dao.ProfessionDao
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        /**
         * This function is callback function after database is created
         * It will be executed only at the time after database is created
         * [database] is an instance for AppDatabase*/
        fun onCreate(scope: CoroutineScope, database: AppDatabase?) {
            val list = listOf(
                ProfessionTable(professionName = "Ui/Ux Designer"),
                ProfessionTable(professionName = "Android Developer"),
                ProfessionTable(professionName = "Node Developer"),
                ProfessionTable(professionName = "React Developer"),
                ProfessionTable(professionName = "Laravel Developer"),
                ProfessionTable(professionName = "Django Developer"),
                ProfessionTable(professionName = "QA Engineer"),
            )
            scope.launch {
                database?.getProfessionDao()?.insert(list)
            }
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
val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // We can perform actions like
        /*database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")*/
    }
}