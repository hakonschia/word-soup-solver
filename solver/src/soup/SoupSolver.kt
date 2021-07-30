package soup

import util.Coordinates

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
        board.forEachIndexed { outerIndex, array ->
            array.forEachIndexed { innerIndex, c ->
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
            this::checkUpwardsDiagonal
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
     * Checks the columns word a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkColumns(word: String): SoupWordSolution? {
        TODO()
    }

    /**
     * Checks the reverse columns for a word (i.e. columns going upwards)
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkReverseColumns(word: String): SoupWordSolution? {
        TODO()
    }

    /**
     * Checks the downwards diagonal for a word
     *
     * @return A solution, or `null` if no solution was found
     */
    private fun checkDownwardsDiagonal(word: String): SoupWordSolution? {
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
}