package app.prgghale.roomdb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import app.prgghale.roomdb.data.dao.UsersDao
import app.prgghale.roomdb.data.table.UserTable

@Database(
    entities = [
        UserTable::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

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