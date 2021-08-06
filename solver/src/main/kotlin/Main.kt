import androidx.compose.desktop.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import soup.SoupSolver
import soup.SoupWordSolution

const val EXIT_MESSAGE = "-1"

/*
fun main() {
    val board = arrayOf(
        charArrayOf('E', 'S', 'L', 'F', 'V', 'J', 'C', 'Y', 'Ø', 'K', 'E', 'T', 'T', 'P'),
        charArrayOf('R', 'N', 'E', 'S', 'P', 'O', 'M', 'S', 'W', 'P', 'Z', 'B', 'D', 'A'),
        charArrayOf('E', 'O', 'T', 'Q', 'W', 'S', 'Y', 'U', 'T', 'L', 'Ø', 'A', 'G', 'M'),
        charArrayOf('A', 'M', 'I', 'M', 'F', 'Ø', 'D', 'S', 'N', 'O', 'G', 'P', 'S', 'D'),
        charArrayOf('T', 'F', 'S', 'S', 'Ø', 'Z', 'S', 'E', 'T', 'N', 'K', 'I', 'F', 'B'),
        charArrayOf('Å', 'C', 'F', 'Æ', 'P', 'D', 'N', 'J', 'O', 'I', 'V', 'U', 'Z', 'E'),
        charArrayOf('T', 'N', 'T', 'V', 'R', 'N', 'R', 'S', 'L', 'T', 'L', 'D', 'Æ', 'R'),
        charArrayOf('I', 'E', 'S', 'N', 'E', 'E', 'T', 'O', 'W', 'E', 'V', 'T', 'U', 'M'),
        charArrayOf('A', 'G', 'R', 'V', 'N', 'I', 'G', 'R', 'F', 'R', 'F', 'Å', 'E', 'E'),
        charArrayOf('Y', 'R', 'S', 'I', 'K', 'E', 'S', 'E', 'Z', 'G', 'S', 'K', 'N', 'S'),
        charArrayOf('B', 'O', 'F', 'E', 'D', 'R', 'P', 'V', 'N', 'E', 'D', 'Y', 'K', 'Æ'),
        charArrayOf('L', 'M', 'R', 'W', 'F', 'Ø', 'X', 'H', 'E', 'W', 'T', 'D', 'H', 'V'),
        charArrayOf('K', 'E', 'T', 'J', 'F', 'V', 'Æ', 'K', 'C', 'I', 'Z', 'G', 'S', 'D'),
        charArrayOf('O', 'T', 'Ø', 'M', 'S', 'U', 'N', 'D', 'D', 'P', 'S', 'G', 'J', 'L')
    )

    val solver = SoupSolver(board)

    do {
        // Adding the generated board to the multiline string messes up the output, so whatever
        println(
            "Current board:\n${solver.generateBoard()}\n"
        )

        println("Type in the word to find, or $EXIT_MESSAGE to exit")
        val word = readLine()
        println()

        if (word != null) {
            val solution = solver.findWord(word)

            // Solution was found
            if (solution != null) {
                println(
                    """
                        Found '$word' on the board!
                            Found from column ${solution.startCoordinates.x} to column ${solution.endCoordinates.x}
                            Found from row ${solution.startCoordinates.y} to row ${solution.endCoordinates.y}
                    """.trimIndent()
                )
            } else {
                println("'$word' was not found on the board")
            }

            println("---------------------------------\n\n")
        } else {
            println("Error reading input, exiting")
        }
    } while (word != null && word != EXIT_MESSAGE)

    println("\n\n\nExiting, final board:\n${solver.generateBoard()}")
}
 */

@OptIn(ExperimentalFoundationApi::class)
fun main() = Window {
    val board = arrayOf(
        charArrayOf('E', 'S', 'L', 'F', 'V', 'J', 'C', 'Y', 'Ø', 'K', 'E', 'T', 'T', 'P'),
        charArrayOf('R', 'N', 'E', 'S', 'P', 'O', 'M', 'S', 'W', 'P', 'Z', 'B', 'D', 'A'),
        charArrayOf('E', 'O', 'T', 'Q', 'W', 'S', 'Y', 'U', 'T', 'L', 'Ø', 'A', 'G', 'M'),
        charArrayOf('A', 'M', 'I', 'M', 'F', 'Ø', 'D', 'S', 'N', 'O', 'G', 'P', 'S', 'D'),
        charArrayOf('T', 'F', 'S', 'S', 'Ø', 'Z', 'S', 'E', 'T', 'N', 'K', 'I', 'F', 'B'),
        charArrayOf('Å', 'C', 'F', 'Æ', 'P', 'D', 'N', 'J', 'O', 'I', 'V', 'U', 'Z', 'E'),
        charArrayOf('T', 'N', 'T', 'V', 'R', 'N', 'R', 'S', 'L', 'T', 'L', 'D', 'Æ', 'R'),
        charArrayOf('I', 'E', 'S', 'N', 'E', 'E', 'T', 'O', 'W', 'E', 'V', 'T', 'U', 'M'),
        charArrayOf('A', 'G', 'R', 'V', 'N', 'I', 'G', 'R', 'F', 'R', 'F', 'Å', 'E', 'E'),
        charArrayOf('Y', 'R', 'S', 'I', 'K', 'E', 'S', 'E', 'Z', 'G', 'S', 'K', 'N', 'S'),
        charArrayOf('B', 'O', 'F', 'E', 'D', 'R', 'P', 'V', 'N', 'E', 'D', 'Y', 'K', 'Æ'),
        charArrayOf('L', 'M', 'R', 'W', 'F', 'Ø', 'X', 'H', 'E', 'W', 'T', 'D', 'H', 'V'),
        charArrayOf('K', 'E', 'T', 'J', 'F', 'V', 'Æ', 'K', 'C', 'I', 'Z', 'G', 'S', 'D'),
        charArrayOf('O', 'T', 'Ø', 'M', 'S', 'U', 'N', 'D', 'D', 'P', 'S', 'G', 'J', 'L')
    )

    val solver = SoupSolver(board)

    Column {
        Row(
            modifier = Modifier.padding(start = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var input by remember { mutableStateOf("") }
            TextField(
                value = input,
                onValueChange = {
                    input = it
                }, label = {
                    Text("Word")
                }
            )

            Button(
                onClick = {
                    if (input.isNotEmpty()) {
                        val solution = solver.findWord(input)
                    }
                },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text("Find")
            }
        }

        Row {
            LazyVerticalGrid(cells = GridCells.Fixed(board.size)) {
                items(count = board.size * board.size) { pos ->
                    BoardCell(board, pos)
                }
            }
        }
    }
}


/**
 * Composable for displaying a grid cell on a board
 *
 * @param board The board to use
 * @param position The position in the board this cell
 */
@Composable
fun BoardCell(board: Array<CharArray>, position: Int) {
    // Size of the cell
    val size = 35.dp

    val row = position / board.size
    val column = position % board.size

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(size)
            .width(size)
            .padding(2.dp)
            .border(
                width = 1.dp,
                color = Color.Gray
            )
    ) {
        Text(
            text = board[row][column].uppercase()
        )
    }

}