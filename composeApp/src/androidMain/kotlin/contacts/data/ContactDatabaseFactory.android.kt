package contacts.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.raa.agendatelefono.database.ContactDatabase

actual class ContactDatabaseFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(

            ContactDatabase.Schema,     context, "contacts.db"
        )
    }

}