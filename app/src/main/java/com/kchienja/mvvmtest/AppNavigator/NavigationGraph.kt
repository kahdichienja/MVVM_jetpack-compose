package com.kchienja.mvvmtest.AppNavigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.kchienja.mvvmtest.DetailedViwPage
import com.kchienja.mvvmtest.todo.TodoActivityNavigationEntryScreen


@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationScreens.TodoScreen.route){
        composable(route = NavigationScreens.TodoScreen.route){
            TodoActivityNavigationEntryScreen(navController = navController)
        }


        composable(route = NavigationScreens.MainScreen.route){
            DetailedViwPage(navController = navController)
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