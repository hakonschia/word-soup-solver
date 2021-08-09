import androidx.compose.desktop.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.dp
import soup.SoupSolver
import soup.SoupWordSolution
import soup.WordDirection
import util.SizeDp
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * The size of a cell in the board
 */
val CELL_SIZE = 35.dp

/**
 * The padding around a cell in the board
 */
val CELL_PADDING = 2.dp

/**
 * The color for word solutions
 */
val Color.Companion.WordSolution
    get() = Color(0x88FF3838)

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

@OptIn(ExperimentalFoundationApi::class)
fun main() = Window {
    var solutions by remember { mutableStateOf(listOf<SoupWordSolution>()) }

    Column {
        Row(
            modifier = Modifier.padding(start = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var input by remember { mutableStateOf("") }
            var lastSubmittedSolution by remember { mutableStateOf("") }

            /**
             * Calls [SoupSolver.findWord] with the value of input and updates the solutions if found
             *
             * Does not attempt to submit duplicates right after each other, or empty strings
             */
            fun submitSolution() {
                if (input.isNotEmpty() && input != lastSubmittedSolution) {
                    lastSubmittedSolution = input
                    val solution = solver.findWord(input)
                    if (solution != null) {
                        solutions = ArrayList(solver.solutions)
                        input = ""
                    }
                }
            }

            TextField(
                value = input,
                onValueChange = {
                    input = it
                }, label = {
                    Text("Word")
                },
                singleLine = true,
                modifier = Modifier.onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown && event.key== Key.Enter) {
                        submitSolution()
                    }
                    false
                },

            )

            Button(
                onClick = { submitSolution() },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text("Find")
            }
        }

        Row {
            // Box allows for views to overlap
            Box {
                LazyColumn {
                    itemsIndexed(board) { rowPos, row ->
                        LazyRow {
                            items(row.size) { column ->
                                BoardCell(board, rowPos, column)
                            }
                        }
                    }
                }

                // Add solutions after so that it is rendered over the board
                solutions.forEach { solution ->
                    SolutionArrow(solution)
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
        verticalArrangement = Arrangement.Center,
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
    val size = when (solution.direction) {
        WordDirection.HORIZONTAL, WordDirection.HORIZONTAL_REVERSE, WordDirection.VERTICAL, WordDirection.VERTICAL_REVERSE -> {
            SizeDp(width = CELL_SIZE * solution.word.length, height = CELL_SIZE)
        }

        // This is pythagoras
        WordDirection.DIAGONAL_DOWN, WordDirection.DIAGONAL_DOWN_REVERSE, WordDirection.DIAGONAL_UP, WordDirection.DIAGONAL_UP_REVERSE -> {
            val cathetus = (CELL_SIZE * solution.word.length).value.pow(2)
            val hypotenuse = sqrt(cathetus + cathetus)

            SizeDp(width = hypotenuse.dp, height = CELL_SIZE)
        }
    }

    val rotation = when (solution.direction) {
        WordDirection.HORIZONTAL -> 0
        WordDirection.HORIZONTAL_REVERSE -> 180
        WordDirection.VERTICAL -> 90
        WordDirection.VERTICAL_REVERSE -> 270
        WordDirection.DIAGONAL_DOWN -> 45
        WordDirection.DIAGONAL_DOWN_REVERSE -> 135
        WordDirection.DIAGONAL_UP -> 315
        WordDirection.DIAGONAL_UP_REVERSE -> 225
    }

    // first = x, second = y
    val paddings = when (solution.direction) {
        WordDirection.HORIZONTAL -> 0.dp to 0.dp

        // For the rotation to end up at the correct place we need to offset it as well
        // The rotation point is at the halfway on Y, and doesn't move when rotated
        // So the paddings/offsets below are correcting the rotation point

        // Starts at the right cell, but will be flipped so must be moved one cell to the right
        WordDirection.HORIZONTAL_REVERSE -> CELL_SIZE to 0.dp

        WordDirection.VERTICAL -> CELL_SIZE * 0.5f to -CELL_SIZE * 0.5f
        WordDirection.VERTICAL_REVERSE -> CELL_SIZE * 0.5f to CELL_SIZE * 0.5f

        WordDirection.DIAGONAL_DOWN -> 0.dp to -CELL_SIZE * 0.5f
        // The reverse of above offsets one cell to the right on the x-axis
        WordDirection.DIAGONAL_DOWN_REVERSE -> CELL_SIZE to -CELL_SIZE * 0.5f

        // These two are the same as the other diagonals, but positive Y
        WordDirection.DIAGONAL_UP -> 0.dp to CELL_SIZE * 0.5f
        WordDirection.DIAGONAL_UP_REVERSE -> CELL_SIZE to CELL_SIZE * 0.5f
    }


    val x = CELL_SIZE * solution.startCoordinates.x + paddings.first
    val y = CELL_SIZE * solution.startCoordinates.y + paddings.second

    Column(
        modifier = Modifier
            .height(size.height)
            .width(size.width)
            .offset(x, y)
            .graphicsLayer {
                if (rotation != 0) {
                    // The transform origin determines where the rotation is done. Default is at the center (0.5f, 0.5f)
                    // The rotation point will be stationary, i.e. it's the same at the start and end rotation

                    // (0, 0.5f) means all the way to the left and halfway down, 0% on x and 50% on y
                    // I.e., the rotation point is at the star:
                    // - - - -
                    // *     |
                    // - - - -
                    transformOrigin = TransformOrigin(0f, 0.5f)

                    // We have to set the rotation here and not on the modifier, since the modifier doesn't follow
                    // the transform origin
                    rotationZ = rotation.toFloat()
                }
            }
            .border(
                width = 2.dp,
                color = Color.WordSolution,
                shape = RoundedCornerShape(50)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            painter = svgResource("ic_round_arrow_forward_24.xml"),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
        )
    }
}