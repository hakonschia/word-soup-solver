package soup

import util.Coordinates

/**
 * @param boardToSolve The board to solve (must be an n*n square)
 */
class SoupSolver(
    boardToSolve: Array<CharArray>
) {

    /**
     * The board to solve
     */
    private val board: Array<CharArray>

    /**
     * The board being solved, with the current solutions added as upper case characters
     */
    private val boardWithSolutions: Array<CharArray>

    /**
     * The internal solutions found so far
     */
    private val _solutions: MutableList<SoupWordSolution> = ArrayList()

    val solutions :List<SoupWordSolution> = _solutions

    init {
        board = boardToSolve.clone()

        // Ensure all characters are lowercased as a lowercase letter is seen as "no solution" for that given character
        board.forEachIndexed { outerIndex, row ->
            // Check that each row is equal size to the column (the board is a perfect square)
            check(row.size == board.size) {
                "Given board is not a square"
            }

            row.forEachIndexed { innerIndex, c ->
                board[outerIndex][innerIndex] = c.lowercaseChar()
            }
        }

        boardWithSolutions = board.clone()
    }

    /**
     * Finds a word in the board
     *
     * @param word The word to find
     *
     * @return The solution for the word, or `null` if the word wasn't found
     */
    fun findWord(word: String): SoupWordSolution? {
        val checkers = listOf(
            this::checkRows,
            this::checkReverseRows,
            this::checkColumns,
            this::checkReverseColumns,
            this::checkDownwardsDiagonal,
            this::checkReverseDownwardsDiagonal,
            this::checkUpwardsDiagonal,
            this::checkReverseUpwardsDiagonal
        )

        // Call each function until we find a match
        checkers.forEach { checker ->
            val solution = checker.invoke(word)

            if (solution != null) {
                _solutions.add(solution)
                modifySolutionBoard(solution)

                return solution
            }
        }

        // No solution found
        return null
    }



    /**
     * Generates the current board with the current solutions.
     *
     * @return A string representation of the board. Capital letters indicate a solution
     */
    fun generateBoard(): String {
        val solution = boardWithSolutions.clone()
        val solutionAsRawString = StringBuilder()

        solution.forEachIndexed { index, array ->
            solutionAsRawString.append(array.joinToString(" "))

            // Don't add a newline after the last line
            if (index + 1 != solution.size) {
                solutionAsRawString.append("\n")
            }
        }

        return solutionAsRawString.toString()
    }

    /**
     * Modifies [boardWithSolutions] with a solution
     */
    private fun modifySolutionBoard(solution: SoupWordSolution) {
        // Change boardWithSolutions so that the word is capitalized in the matrix
    }


    // The only thing we really need to do to check if a word is valid is build a string of the lines
    // going horizontal, vertical, diagonally etc. and check if the word is a substring of the word we built
    // Then we need to figure out the coordinates of where it starts and ends

    // (0, 0) in the board is the top left, so some logic below is somewhat reversed of what is natural
    // E.g. the image below is the downwards diagonal
    /*
    - - - . - - -
    - - - - . - -
    - - - - - . -
     */


    /**
     * Checks the rows for a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkRows(word: String): SoupWordSolution? {
        board.forEachIndexed { rowIndex, row ->
            val rowAsString = row.joinToString("")

            // We need to ignore the case as words can overlap, which means it can match either
            val startPos = rowAsString.indexOf(word, ignoreCase = true)

            if (startPos != -1) {
                return SoupWordSolution(
                    word = word,
                    startCoordinates = Coordinates(x = startPos, y = rowIndex),
                    // -1 as "WORD" ends at index 3, not 4
                    endCoordinates = Coordinates(x = startPos + word.length - 1, y = rowIndex),
                    direction = WordDirection.HORIZONTAL
                )
            }
        }

        // No solution found
        return null
    }

    /**
     * Checks the reverse rows for a word (i.e. backwards words on the rows)
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkReverseRows(word: String): SoupWordSolution? {
        board.forEachIndexed { rowIndex, row ->
            val rowAsString = row.joinToString("").reversed()

            // We need to ignore the case as words can overlap, which means it can match either
            val startPos = rowAsString.indexOf(word, ignoreCase = true)

            if (startPos != -1) {
                return SoupWordSolution(
                    word = word,
                    // The start and end for reversed words are also reversed

                    // For row: RNESPOMSWPZBDA
                    // The reversed word "MOPSEN" is from index 1 to 6 (in the original word as "NESPOM")

                    // Reverse the row: ADBZPWSMOPSENR
                    // startPos = 7

                    // -1 as "WORD" ends at index 3, not 4
                    // 14 - 7 - 1 = 6
                    startCoordinates = Coordinates(x = rowAsString.length - startPos - 1, y = rowIndex),

                    // 14 - 7 - 6 = 1
                    endCoordinates = Coordinates(x = rowAsString.length - startPos - word.length, y = rowIndex),
                    direction = WordDirection.HORIZONTAL_REVERSE
                )
            }
        }

        // No solution found
        return null
    }


    /**
     * Builds a word from [board] for a given column
     *
     * @param column The column to build on (no checks is done that this is a valid number)
     * @return The word built on the given column
     */
    private fun buildColumnWord(column: Int): String {
        val columnWordBuilder = StringBuilder()

        // Append the character at the column for each row to build the word
        board.forEach { row ->
            columnWordBuilder.append(row[column])
        }

        return columnWordBuilder.toString()
    }

    /**
     * Checks the columns word a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkColumns(word: String): SoupWordSolution? {
        for (rowIndex in board.indices) {
            val columnWord = buildColumnWord(rowIndex)

            // We need to ignore the case as words can overlap, which means it can match either
            val startPos = columnWord.indexOf(word, ignoreCase = true)

            if (startPos != -1) {
                return SoupWordSolution(
                    word = word,
                    startCoordinates = Coordinates(x = rowIndex, y = startPos),
                    // -1 as "WORD" ends at index 3, not 4
                    endCoordinates = Coordinates(x = rowIndex, y = startPos + word.length - 1),
                    direction = WordDirection.VERTICAL
                )
            }
        }

        // No solution found
        return null
    }

    /**
     * Checks the reverse columns for a word (i.e. columns going upwards)
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkReverseColumns(word: String): SoupWordSolution? {
        for (rowIndex in board.indices) {
            val columnWord = buildColumnWord(rowIndex).reversed()

            // We need to ignore the case as words can overlap, which means it can match either
            val startPos = columnWord.indexOf(word, ignoreCase = true)

            if (startPos != -1) {
                return SoupWordSolution(
                    word = word,

                    // See documentation for the reversed rows
                    // Using "board.size" we assume an n*n square (which is currently true based on init{}"
                    startCoordinates = Coordinates(x = rowIndex, y = board.size - startPos - 1),
                    endCoordinates = Coordinates(x = rowIndex, y = board.size - startPos - word.length),
                    direction = WordDirection.VERTICAL_REVERSE
                )
            }
        }

        // No solution found
        return null
    }


    /**
     * Builds a word on the downward diagonal for a column in [board]
     *
     * @param columnOrRow The index of the column or row to build the diagonal on
     * @param buildForColumns If true, [columnOrRow] indicates the index of a column, if false it indicates the index of a row
     */
    private fun buildDownwardDiagonalOnColumns(columnOrRow: Int, buildForColumns: Boolean): String {
        val wordBuilder = StringBuilder()

        for (i in 0 until board.size - columnOrRow) {
            // The diagonal is offset by the column/row index
            if (buildForColumns) {
                wordBuilder.append(board[i][i + columnOrRow])
            } else {
                wordBuilder.append(board[i + columnOrRow][i])
            }
        }

        return wordBuilder.toString()
    }


    /**
     * Checks the downwards diagonal for a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkDownwardsDiagonal(word: String): SoupWordSolution? {
        for (rowIndex in board.indices) {
            // On an n*n square we can use rowIndex, but if we're not on n*n this wouldn't work
            val columnWord = buildDownwardDiagonalOnColumns(rowIndex, buildForColumns = true)

            // We need to ignore the case as words can overlap, which means it can match either
            val startPosColumn = columnWord.indexOf(word, ignoreCase = true)

            if (startPosColumn != -1) {
                return SoupWordSolution(
                    word = word,
                    startCoordinates = Coordinates(x = rowIndex + startPosColumn, y = startPosColumn),
                    endCoordinates = Coordinates(x = rowIndex + startPosColumn + word.length - 1, y = startPosColumn + word.length - 1),
                    direction = WordDirection.DIAGONAL_DOWN
                )
            }

            val rowWord = buildDownwardDiagonalOnColumns(rowIndex, buildForColumns = false)

            // We need to ignore the case as words can overlap, which means it can match either
            val startPosRow = rowWord.indexOf(word, ignoreCase = true)

            if (startPosRow != -1) {
                return SoupWordSolution(
                    word = word,

                    startCoordinates = Coordinates(x = startPosRow, y = rowIndex + startPosRow),
                    endCoordinates = Coordinates(x = startPosRow + word.length - 1, y = rowIndex + startPosRow + word.length - 1),
                    direction = WordDirection.DIAGONAL_DOWN
                )
            }
        }

        // No solution found
        return null
    }

    /**
     * Checks the reverse downwards diagonal for a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkReverseDownwardsDiagonal(word: String): SoupWordSolution? {
        TODO()
    }

    /**
     * Checks the upwards diagonal for a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkUpwardsDiagonal(word: String): SoupWordSolution? {
        TODO()
    }

    /**
     * Checks the reverse upwards diagonal for a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkReverseUpwardsDiagonal(word: String): SoupWordSolution? {
        TODO()
    }
}