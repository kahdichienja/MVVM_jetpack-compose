package com.kchienja.mvvmtest.AppNavigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.kchienja.mvvmtest.DetailedViwPage
import com.kchienja.mvvmtest.SplashScreen
import com.kchienja.mvvmtest.todo.TodoActivityNavigationEntryScreen


@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationScreens.SplashScreen.route){
        composable(route = NavigationScreens.SplashScreen.route){
            SplashScreen(navController = navController)
        }

        composable(route = NavigationScreens.MainScreen.route){
            DetailedViwPage(navController = navController)
        }
        composable(route = NavigationScreens.TodoDataClassScreen.route){
            TodoActivityNavigationEntryScreen(navController = navController)
        }

        composable(
            route = NavigationScreens.DetailScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "Clinton"
                }
            )
        ){
//                entry -> {}
//            DetailScreen(name = entry.arguments?.getString("name"))
        }

    }

}