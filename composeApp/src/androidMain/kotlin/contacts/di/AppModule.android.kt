package contacts.di

import android.content.Context
import contacts.data.ContactDatabaseFactory
import contacts.data.SqlDelightContactDataSource
import contacts.domain.ContactDataSource
import org.raa.agendatelefono.database.ContactDatabase

actual class AppModule(private val context: Context) {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(db = ContactDatabase(
            driver = ContactDatabaseFactory(context).create()
        ))
    }




}