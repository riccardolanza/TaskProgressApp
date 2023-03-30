package com.example.taskprogress13

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.taskprogress13.ui.screens.AuthenticationFormScreen

/**
 * Contract for information needed on every Rally navigation destination
 */
interface TaskProgressDestination {

    //    val icon: ImageVector
    val route: String
    val title: String
}

/**
 * Rally app navigation destinations
 */
object StartScreenDestination : TaskProgressDestination {
//    override val icon = Icons.Filled.PieChart
    override val route = "start_screen"
    override val title = "Riepilogo"

}

object AllTaskExecutionsScreenDestination : TaskProgressDestination {
//    override val icon = Icons.Filled.AttachMoney
    override val route = "all_tasks_screen"
    override val title = "Tutte le esecuzioni"
}

object TaskExecutionEntryScreenDestination : TaskProgressDestination {
//    override val icon = Icons.Filled.MoneyOff
    override val route = "task_execution_entry_screen"
    override val title = "Inserimento esecuzione"
}

object TaskDetailScreenDestination : TaskProgressDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the RallyTabRow selection
 //   override val icon = Icons.Filled.Money
    override val route = "task_detail_screen"
    override val title = "Dettaglio esecuzioni"
    const val taskNameArg = "task_name"
    val routeWithArgs = "${route}/{${taskNameArg}}"
    val arguments = listOf(
        navArgument(taskNameArg) {
            type = NavType.StringType
            defaultValue = "Tutti"
        }
    )
}
object AdministrationScreenDestination : TaskProgressDestination {
    //    override val icon = Icons.Filled.AttachMoney
    override val route = "administration_screen"
    override val title = "Amministrazione"
}

object AllAvailableAwardsScreenDestination : TaskProgressDestination {
    //    override val icon = Icons.Filled.AttachMoney
    override val route = "all_available_awards_screen"
    override val title = "Tutti i premi disponibili"
}

object AwardEntryScreenDestination : TaskProgressDestination {
    //    override val icon = Icons.Filled.AttachMoney
    override val route = "award_entry_screen"
    override val title = "Inserimento premi"
}

object AllEligibleAwardsScreenDestination : TaskProgressDestination {
    //    override val icon = Icons.Filled.AttachMoney
    override val route = "all_eligible_awards_screen"
    override val title = "Lista dei premi utilizzabili"
}

object AllUsedAwardsScreenDestination : TaskProgressDestination {
    //    override val icon = Icons.Filled.AttachMoney
    override val route = "all_used_awards_screen"
    override val title = "Lista dei premi utilizzati"
}

object UsedAwardsByTaskNameScreenDestination : TaskProgressDestination {
    override val route = "used_awards_by_task_name_screen"
    override val title = "Premi fruiti per"
    const val taskNameArg = "task_name"
    val routeWithArgs = "${route}/{${taskNameArg}}"
    val arguments = listOf(
        navArgument(taskNameArg) {
            type = NavType.StringType
            defaultValue = "Tutti"
        }
    )
}

object AuthenticationFormScreenDestination : TaskProgressDestination {
    //    override val icon = Icons.Filled.AttachMoney
    override val route = "authentication_form_screen"
    override val title = "Form di autenticazione per l'amministrazione"
}

object TaskDetailScreenDestinationRemoteDB : TaskProgressDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the RallyTabRow selection
    //   override val icon = Icons.Filled.Money
    override val route = "task_detail_screen_remote_DB"
    override val title = "Dettaglio esecuzioni su DB remoto"
    const val taskNameArg = "task_name"
    val routeWithArgs = "${route}/{${taskNameArg}}"
    val arguments = listOf(
        navArgument(taskNameArg) {
            type = NavType.StringType
            defaultValue = "Tutti"
        }
    )
}

val taskProgressScreens = listOf(
    StartScreenDestination,
    AllTaskExecutionsScreenDestination,
    TaskExecutionEntryScreenDestination,
    TaskDetailScreenDestination,
    AdministrationScreenDestination,
    AllAvailableAwardsScreenDestination,
    AwardEntryScreenDestination,
    AllEligibleAwardsScreenDestination,
    AllUsedAwardsScreenDestination,
    UsedAwardsByTaskNameScreenDestination,
    AuthenticationFormScreenDestination,
    TaskDetailScreenDestinationRemoteDB
)

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }