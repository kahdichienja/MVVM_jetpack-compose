package com.kchienja.mvvmtest.AppNavigator

sealed class NavigationScreens(val route: String) {
    object SplashScreen : NavigationScreens("splash_screen")
    object TodoScreen : NavigationScreens("todo_screen")
    object TodoDataClassScreen : NavigationScreens("todo-data_class_screen")
    object MainScreen : NavigationScreens("main_screen")
    object DetailScreen : NavigationScreens("detail_screen")

    fun withArgs(vararg args: String) : String{
        return  buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}