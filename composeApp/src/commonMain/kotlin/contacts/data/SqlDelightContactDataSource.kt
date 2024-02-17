package contacts.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import contacts.domain.Contact
import contacts.domain.ContactDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import org.raa.agendatelefono.database.ContactDatabase

class SqlDelightContactDataSource(
    db: ContactDatabase
) : ContactDataSource{

    private val queries = db.contactQueries

    override fun getContacts(): Flow<List<Contact>> {
        return queries.getContacts().asFlow().mapToList().map {it ->

            it.map { it.toContact() }

        }
    }

    override fun getRecentContacts(amount: Long): Flow<List<Contact>> {
        return queries.getRecentContacts(amount).asFlow().mapToList().map {it ->

            it.map { it.toContact() }

        }
    }

    override suspend fun insertContact(contact: Contact) {
       queries.insertContactEntity(
            id = contact.id,
            firstName = contact.firstName,
            lastName = contact.lastName,
            email = contact.email,
           phoneNumber = contact.phoneNumber,
           createdAt = Clock.System.now().toEpochMilliseconds(),
           imagePath = null
       )

    }

    override suspend fun deleteContact(id: Long) {
        queries.deleteContact(id)
    }

    override fun getbyId(id: Long): Flow<Contact> {
        return queries.getContactById(id).asFlow().mapToOne().map { it.toContact() }
    }

}