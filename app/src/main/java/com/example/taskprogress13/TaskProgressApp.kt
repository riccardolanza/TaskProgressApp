package com.example.taskprogress13


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.taskprogress13.ui.screens.*
import com.example.taskprogress13.ui.viewmodel.TaskProgressUiState
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel
import com.example.taskprogress13.ui.viewmodel.isValid
import kotlinx.coroutines.launch



@Composable
fun TaskProgressApp(
    modifier: Modifier = Modifier,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    navController: NavHostController = rememberNavController()
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    // Fetch your currentDestination:
    val currentDestination = currentBackStack?.destination
    // Change the variable to this and use Overview as a backup screen if this returns null
    val currentScreen = taskProgressScreens.find { it.route == currentDestination?.route } ?: StartScreenDestination
    val coroutineScope = rememberCoroutineScope()
    val taskExecutionList by viewModel.getAllTaskExecutions().collectAsState(emptyList())
    val allAwardsList by viewModel.getAllAwards().collectAsState(emptyList())
    val focusManager = LocalFocusManager.current
    var topAppBarTitle: String = ""

    Scaffold(
        topBar = {
            TaskProgressTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    viewModel.resetTaskProgressUiState()
                    navController.navigateUp()
                },
                topAppBarTitle = topAppBarTitle,
                onActionsClick = {
                    navController.navigate(AdministrationScreenDestination.route)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = StartScreenDestination.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = StartScreenDestination.route) {
                StartScreen(
                    navigateToTaskExecutionEntryScreen={
                        navController.navigate(TaskExecutionEntryScreenDestination.route)
                    },
                    navigateToAllTasksScreen = {
                        navController.navigate(AllTaskExecutionsScreenDestination.route)
                    },
                    onButtonClicked = {taskName ->
                        navController
                            .navigateSingleTopTo("${TaskDetailScreenDestination.route}/$taskName")
                    }
                )
                topAppBarTitle = StartScreenDestination.title
            }

            composable(
                route = TaskDetailScreenDestination.routeWithArgs,
                arguments =  TaskDetailScreenDestination.arguments
            ) {
                    navBackStackEntry ->
                // Retrieve the passed argument
                val taskName =
                    navBackStackEntry.arguments?.getString(TaskDetailScreenDestination.taskNameArg)

                // Pass accountType to SingleAccountScreen
                if (taskName != null) {
                    TaskDetailScreen(
                        taskName=taskName,
                        navigateToTaskExecutionEntryScreen={
                            viewModel.updateUiStateTaskName(taskName)
                            navController.navigate(TaskExecutionEntryScreenDestination.route)
                        },
                    )
                }
                topAppBarTitle = TaskDetailScreenDestination.title + " " + taskName


            }
            composable(route = AllTaskExecutionsScreenDestination.route) {
//                val context = LocalContext.current
                AllTaskExecutionsScreen(
                    taskExecutionList=taskExecutionList
                )
                topAppBarTitle=AllTaskExecutionsScreenDestination.title
            }

            composable(route = TaskExecutionEntryScreenDestination.route) {
                 TaskExecutionEntryScreen(
                    taskProgressUiState = viewModel.taskProgressUiState,
                    onTaskExecutionValueChange = viewModel::updateTaskProgressUiState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.saveTaskExecution()
                        }
                        focusManager.clearFocus()
                    },
                    onFABclick = {
                        viewModel.resetTaskProgressUiState()
                        navController.navigateUp()
                    },
                )
                topAppBarTitle=TaskExecutionEntryScreenDestination.title
            }

            composable(route = AdministrationScreenDestination.route) {
                AdministrationScreen(
                    onAllAvailableAwardsButtonClick = {navController.navigate(AllAvailableAwardsScreenDestination.route)}
                )
                topAppBarTitle=AdministrationScreenDestination.title
            }

            composable(route = AllAvailableAwardsScreenDestination.route) {
                AllAvailableAwardsScreen(
                    navigateToAwardEntryScreen={
                        navController.navigate(AwardEntryScreenDestination.route)
                    },
                )
                topAppBarTitle=AllAvailableAwardsScreenDestination.title
            }

            composable(route = AwardEntryScreenDestination.route) {
                AwardEntryScreen(
                    awardUiState = viewModel.awardUiState,
                    onAwardValueChange = viewModel::updateAwardUiState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.saveAward()
                        }
                        focusManager.clearFocus()
                    },
                    onFABclick = {
                        viewModel.resetAwardUiState()
                        navController.navigateUp()
                    },
                )
                topAppBarTitle=AwardEntryScreenDestination.title
            }
        }
    }
}





@Composable
fun TaskProgressTopAppBar(
    modifier: Modifier = Modifier,
    currentScreen: TaskProgressDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    topAppBarTitle: String,
    onActionsClick: () -> Unit
) {
    TopAppBar(
        title = {Text(topAppBarTitle)} ,
        //{Text(stringResource(R.string.taskName)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onActionsClick ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        }
    )
}
