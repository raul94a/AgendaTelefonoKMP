package contacts.di

import contacts.data.ContactDatabaseFactory
import contacts.data.SqlDelightContactDataSource
import contacts.domain.ContactDataSource
import org.raa.agendatelefono.database.ContactDatabase

actual class AppModule {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(db = ContactDatabase(
            driver = ContactDatabaseFactory().create()
        )
        )
    }

}