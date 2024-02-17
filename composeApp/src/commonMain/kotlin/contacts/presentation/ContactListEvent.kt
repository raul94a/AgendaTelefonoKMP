package contacts.presentation

import contacts.domain.Contact

sealed interface ContactListEvent {
    data object OnAddnewContactClick: ContactListEvent
    data object dismissContact: ContactListEvent
    data class OnFirstNameChanged(val value: String): ContactListEvent
    data class OnLastNameChanged(val value: String): ContactListEvent
    data class OnEmailNameChanged(val value: String): ContactListEvent
    data class OnPhoneNumberNameChanged(val value: String): ContactListEvent

    class OnPhotoPicked(val bytes: ByteArray): ContactListEvent
    data object OnAddPhotoClicked : ContactListEvent
    data object SaveContact: ContactListEvent
    data class SelectContact(val contact: Contact) : ContactListEvent
    data class EditContact(val contact: Contact) : ContactListEvent
    data object DeleteContact: ContactListEvent

}