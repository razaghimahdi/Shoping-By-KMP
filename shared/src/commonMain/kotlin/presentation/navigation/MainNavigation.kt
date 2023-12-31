package presentation.navigation

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class MainNavigation(
    val route: String,
    val title: String,
    val selectedIcon: String,
    val unSelectedIcon: String,
) {

    object Home : MainNavigation(
        route = "Home", title = "Home",
        selectedIcon = "home.xml",
        unSelectedIcon = "home_border.xml"
    )

    object Wishlist : MainNavigation(
        route = "Wishlist", title = "Wishlist",
        selectedIcon = "heart2.xml",
        unSelectedIcon = "heart_border2.xml"
    )

    object Cart : MainNavigation(
        route = "Cart", title = "Cart",
        selectedIcon = "cart.xml",
        unSelectedIcon = "cart_border.xml"
    )

    object Profile : MainNavigation(
        route = "Profile", title = "Profile",
        selectedIcon = "profile.xml",
        unSelectedIcon = "profile_border.xml"
    )


}

