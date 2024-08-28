//package com.example.final_finalapp.db
//
//import UserDao
//import androidx.room.Database
//import android.content.Context
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [UserTestBase::class], version = 1)
//abstract class AppDb : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}
//
//
//
//fun createDb(context: Context): AppDb {
//    return Room.databaseBuilder(
//        context.applicationContext,
//        AppDb::class.java,
//        "appDb"
//    ).build()
//}
