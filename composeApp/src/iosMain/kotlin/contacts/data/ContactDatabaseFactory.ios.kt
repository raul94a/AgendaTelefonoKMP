package contacts.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.raa.agendatelefono.database.ContactDatabase


actual class ContactDatabaseFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            ContactDatabase.Schema, "contacts.db"
        )
    }

}