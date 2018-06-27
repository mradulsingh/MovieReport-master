/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.android.example.github.api.GithubService;
import com.android.example.github.api.MovieService;
import com.android.example.github.db.GithubDb;
import com.android.example.github.db.MovieDao;
import com.android.example.github.db.MovieDb;
import com.android.example.github.db.RepoDao;
import com.android.example.github.db.UserDao;
import com.android.example.github.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @Singleton
    @Provides
    public static MovieService provideMovieService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
    }

    @Singleton
    @Provides
    GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }

    @Singleton
    @Provides
    GithubDb provideDb(Application app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db").build();
    }

    @Singleton
    @Provides
    MovieDb provideMovieDb(Application application){
        return Room.databaseBuilder(application, MovieDb.class, "MovieDb.db").build();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(GithubDb db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao(MovieDb movieDb){
        return movieDb.movieDao();
    }

}
