package com.example.mysql.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mysql.ui.home.HomeDestination
import com.example.mysql.ui.home.HomeScreen
import com.example.mysql.ui.item.ItemDetailsDestination
import com.example.mysql.ui.item.ItemDetailsDestination.ItemDetailsScreen
import com.example.mysql.ui.item.ItemEditDestination
import com.example.mysql.ui.item.ItemEditScreen
import com.example.mysql.ui.item.ItemEntryDestination
import com.example.mysql.ui.item.ItemEntryScreen

@Composable
fun MercadinhoNavHost(
    navController: NavHostController,
    modifier : Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    )
    {
        composable(route = HomeDestination.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemDetails = { navController.navigate("${ItemDetailsDestination.route}/${it}") }
            )
        }

        composable(route = ItemEntryDestination.route){
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
           ItemDetailsScreen(
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },

            )
        }
    }
}