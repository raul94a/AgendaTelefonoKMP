package contacts.data

import com.squareup.sqldelight.db.SqlDriver


expect class ContactDatabaseFactory {

    fun create() : SqlDriver
}