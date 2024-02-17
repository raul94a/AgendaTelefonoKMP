
import androidx.compose.ui.window.ComposeUIViewController
import contacts.di.AppModule

import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {

    val isDarkTheme = UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
            UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App(
    isDarkTheme,
    false,
        AppModule()

) }
