package contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import contacts.domain.Contact
import contacts.domain.ContactDataSource
import contacts.domain.ContactValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// a partir de aqui aprender el MOKO para el viewmodel
class ContactListViewModel(
    private val contactDataSource: ContactDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ContactListState(contacts = contacts))
    val state = combine(
        _state,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(20)
    ){ state, contacts, recentContacts ->
        state.copy(
            contacts = contacts,
            recentlyAddedContacts = recentContacts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent){
        when(event){
            ContactListEvent.DeleteContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let { id ->
                        _state.update { it.copy(isSelectedContactSheetOpen = false) }
                        contactDataSource.deleteContact(id)
                        delay(3000)
                        _state.update {
                            it.copy(selectedContact = null)
                        }
                    }


                }
            }
            ContactListEvent.dismissContact-> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isSelectedContactSheetOpen = false,
                            isAddContactSheetOpen = false,
                            firstNameError = null, lastNameError = null,
                            emailError = null, phoneNumberError = null
                        )

                    }
                    delay(3000)
                    newContact = null
                    _state.update { it.copy(selectedContact = null) }
                }

            }
            is ContactListEvent.EditContact -> {

                _state.update {
                    it.copy(
                        selectedContact = event.contact, isAddContactSheetOpen = true, isSelectedContactSheetOpen = false
                    )
                }
                newContact = event.contact
            }
            ContactListEvent.OnAddnewContactClick -> {
            _state.update{
                it.copy(
                    isAddContactSheetOpen = true
                )
             }
                newContact = Contact(
                    id = null,
                    firstName =  "",
                    lastName = "",
                    email = "",
                    phoneNumber = "",
                    photoBytes = null

                )
            }

            is ContactListEvent.OnEmailNameChanged-> {
                newContact = newContact?.copy(email = event.value)
            }
            is ContactListEvent.OnFirstNameChanged-> {
                newContact = newContact?.copy(firstName = event.value)

            }
            is ContactListEvent.OnLastNameChanged -> {
                newContact = newContact?.copy(lastName = event.value)

            }
            is ContactListEvent.OnPhoneNumberNameChanged -> {
                newContact = newContact?.copy(phoneNumber = event.value)

            }
            is ContactListEvent.OnPhotoPicked-> {
                newContact = newContact?.copy(photoBytes = event.bytes)

            }
            ContactListEvent.SaveContact -> {
                newContact?.let {
                    var result = ContactValidator().validateContact(it)
                    val errors = listOfNotNull(result.firstNameError,result.lastNameError,result.emailError,result.phoneNumberError)

                    if(errors.isEmpty()){
                        _state.update { st ->
                            st.copy(
                                isAddContactSheetOpen = false, firstNameError = null, lastNameError = null,
                                emailError = null, phoneNumberError = null
                            )
                        }
                        viewModelScope.launch {
                            contactDataSource.insertContact(it)
                            delay(300L)
                            newContact = null

                        }
                    }
                    else {
                        _state.update { st ->
                            st.copy(
                              firstNameError = result.firstNameError, lastNameError = result.lastNameError,
                                emailError = result.emailError, phoneNumberError = result.phoneNumberError
                            )
                        }
                    }
                }
            }
            is ContactListEvent.SelectContact -> {
                _state.update { st ->
                    st.copy(
                        selectedContact = event.contact,
                        isSelectedContactSheetOpen = true
                    )
                }
            }
            else -> Unit

        }
    }


}


private val contacts = (1..50).map{
    Contact(
        id = it.toLong(),
        firstName = "First${it}",
        lastName = "last${it}",
        phoneNumber = "11239383737",
        email="email@${it}t.com",
        photoBytes = null

    )
}