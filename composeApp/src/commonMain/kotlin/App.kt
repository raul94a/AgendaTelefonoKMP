
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import contacts.di.AppModule
import contacts.domain.Contact
import contacts.domain.ContactDataSource
import contacts.presentation.ContactListViewModel
import contacts.presentation.components.ContactListScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.flow.Flow
import org.raa.agendatelefono.core.presentation.contactsTheme


@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,

) {
    contactsTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {



        val viewModel = getViewModel(
            key = "contactlitscren",
            factory = viewModelFactory {
                ContactListViewModel(appModule.contactDataSource)
            }
        )
        val state by viewModel.state.collectAsState()
        ContactListScreen(
            state=  state,
            newContact = viewModel.newContact,
            onEvent = viewModel::onEvent,
        )
    }
}

class TESTSOURCE : ContactDataSource {
    override fun getContacts(): Flow<List<Contact>> {
        TODO("Not yet implemented")
    }

    override fun getRecentContacts(amount: Long): Flow<List<Contact>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertContact(contact: Contact) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContact(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getbyId(id: Long): Flow<Contact> {
        TODO("Not yet implemented")
    }

}