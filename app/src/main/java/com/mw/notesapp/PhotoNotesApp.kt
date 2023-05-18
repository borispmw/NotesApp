package com.mw.notesapp

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.room.Room
import com.mw.notesapp.persistence.NotesDao
import com.mw.notesapp.persistence.NotesDatabase

class PhotoNotesApp : Application() {
    private var db : NotesDatabase? = null

    init{
        instance = this
    }

    private fun detDb() : NotesDatabase {
        if (db != null){
            return db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                NotesDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

            return db!!
        }
    }

    companion object{
        private var instance: PhotoNotesApp? = null

        fun getDao() : NotesDao {
            return instance!!.detDb().NotesDao()
        }

        fun getUriPermission(uri: Uri){
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
}