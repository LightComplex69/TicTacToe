package wpics.extendedtictactoe.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import wpics.extendedtictactoe.ui.theme.*

/**
 * This composable provides code for the extended tic-tac-toe game.
 *
 * @param modifier A modifier.
 * @param gameViewModel A view model that accompanies this game screen view.
 * @param navController A navigation controller.
 */
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    navController: NavHostController
) {
    // Create a game board object if we have the correct information
    LaunchedEffect(navController) {
        gameViewModel.createGameBoard(navController)
    }

    // View for the game
    // TODO: Add your game layout here. You will need to add functions in GameViewModel to handle button clicks.
    StartNewGameButton(
        onClick = {
            navController.navigate(
                route = MainScreen.Setup.name
            )
        },
        modifier = modifier
    )
}

/**
 * Start game button
 */
@Composable
fun StartNewGameButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Green,
            contentColor = White
        ),
        modifier = modifier
    ) {
        Text("Start New Game")
    }
}

/**
 * The preview screen
 */
@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    ExtendedTicTacToeTheme(dynamicColor = false) {
        GameScreen(navController = rememberNavController())
    }
}