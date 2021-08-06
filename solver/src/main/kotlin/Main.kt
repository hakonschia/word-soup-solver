import androidx.compose.desktop.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import soup.SoupSolver
import soup.SoupWordSolution
import soup.WordDirection
import util.SizeDp

/**
 * The size of a cell in the board
 */
val CELL_SIZE = 35.dp

/**
 * The padding around a cell in the board
 */
val CELL_PADDING = 2.dp

val Color.Companion.WordSolution
    get() = Color(0x88FF3838)


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

    var solutions by remember { mutableStateOf(solver.solutions) }

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
                        if (solution != null) {
                            solutions = solver.solutions
                        }
                    }
                },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text("Find")
            }
        }

        Row {
            // Box allows for views to overlap
            Box {
                // If the grid below is moved then this also has to be moved as the solutions don't have anything
                // that makes them stick to the board
                solutions.forEach { solution ->
                    SolutionArrow(solution)
                }

                LazyColumn {
                    itemsIndexed(board) { rowPos, row ->
                        LazyRow {
                            items(row.size) { column ->
                                BoardCell(board, rowPos, column)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable for displaying a grid cell on a board
 *
 * @param board The board to use
 * @param row The cell row
 * @param column The cell column
 */
@Composable
fun BoardCell(board: Array<CharArray>, row: Int, column: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(CELL_SIZE)
            .width(CELL_SIZE)
            .padding(CELL_PADDING)
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

/**
 * A composable to produce a solution arrow for the board
 *
 * @param solution The solution to produce the arrow for
 */
@Composable
fun SolutionArrow(solution: SoupWordSolution) {
    val isDiagonally = when (solution.direction) {
        WordDirection.HORIZONTAL, WordDirection.HORIZONTAL_REVERSE, WordDirection.VERTICAL, WordDirection.VERTICAL_REVERSE -> false
        WordDirection.DIAGONAL_DOWN, WordDirection.DIAGONAL_DOWN_REVERSE, WordDirection.DIAGONAL_UP, WordDirection.DIAGONAL_UP_REVERSE -> true
    }

    val size = when (solution.direction) {
        WordDirection.HORIZONTAL, WordDirection.HORIZONTAL_REVERSE -> {
            SizeDp(width = CELL_SIZE * solution.word.length, height = CELL_SIZE)
        }

        // Vertical is just horizontal reversed
        WordDirection.VERTICAL, WordDirection.VERTICAL_REVERSE -> {
            SizeDp(width = CELL_SIZE, height = CELL_SIZE * solution.word.length)
        }

        // TODO
        WordDirection.DIAGONAL_DOWN, WordDirection.DIAGONAL_DOWN_REVERSE -> TODO()
        WordDirection.DIAGONAL_UP, WordDirection.DIAGONAL_UP_REVERSE-> TODO()
    }

    val rotation = when (solution.direction) {
        WordDirection.HORIZONTAL, WordDirection.HORIZONTAL_REVERSE -> 0f
        WordDirection.VERTICAL, WordDirection.VERTICAL_REVERSE -> 0f
        WordDirection.DIAGONAL_DOWN, WordDirection.DIAGONAL_DOWN_REVERSE -> 45f
        WordDirection.DIAGONAL_UP, WordDirection.DIAGONAL_UP_REVERSE-> 315f
    }

    // first = x, second = y
    val paddings = when (solution.direction) {
        WordDirection.HORIZONTAL -> 0.dp to 0.dp
        // It's just offset backwards horizontally
        WordDirection.HORIZONTAL_REVERSE -> -CELL_SIZE * (solution.word.length - 1) to 0.dp
        WordDirection.VERTICAL -> 0.dp to 0.dp
        WordDirection.VERTICAL_REVERSE -> 0.dp to -CELL_SIZE * (solution.word.length - 1)

        // TODO
        WordDirection.DIAGONAL_DOWN -> 0.dp to 0.dp
        WordDirection.DIAGONAL_DOWN_REVERSE -> 0.dp to 0.dp
        WordDirection.DIAGONAL_UP -> 0.dp to 0.dp
        WordDirection.DIAGONAL_UP_REVERSE -> 0.dp to 0.dp
    }

    Column(
        modifier = Modifier
            // The paddings are basically the coordinates we start at (start = x, top = y)
            .padding(
                start = CELL_SIZE * solution.startCoordinates.x + paddings.first,
                top = CELL_SIZE * solution.startCoordinates.y + paddings.second
            )
            .height(size.height)
            .width(size.width)
            .rotate(rotation)
            .border(
                width = 2.dp,
                color = Color.WordSolution
            )
    ) { }
}