package com.android.example.github.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.android.example.github.vo.MovieInfoo;

//@Database(entities = {MovieInfoo.class}, version = 1)
public abstract class MovieDb {

    private static final String DB_NAME = "MovieDb.db";

//    abstract public MovieDao movieDao();







    /** Singleton object of this class so that no other object of database is created */
    private static volatile MovieDb instance;

//    static synchronized MovieDb getInstance(Context context){
//        if (instance == null)
//            instance = create(context);
//
//        return instance;
//    }
//    private static MovieDb create(final Context context) {
//        return Room.databaseBuilder(
//                context,
//                MovieDb.class,
//                DB_NAME).build();
//    }









    /** migration class when version number changes */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //doing nothing
        }
    };

}
