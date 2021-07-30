package tests

import org.junit.Test
import soup.SoupSolver
import soup.SoupWordSolution
import soup.WordDirection
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class SoupSolverTest {

    @Test
    fun testValidWords() {
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

        // Words in this board:
        // JFR, SUND, ØKET, TÅTA, JESUS, BERME, SVEIS, MOPSEN, STILTE, MORGEN, FORDØMT, SÆREGEN, SVENNEN, AGNOSTIKER
        var solution: SoupWordSolution?


        // HORIZONTAL WORDS
        // Words: ØKET, SUND

        // "ØKET" is at row 0
        solution = solver.findWord("ØKET")
        assertNotNull(solution)
        assertEquals(WordDirection.HORIZONTAL, solution.direction)
        assertEquals(8, solution.startCoordinates.x)
        assertEquals(11, solution.endCoordinates.x)
        assertEquals(0, solution.startCoordinates.y)
        assertEquals(0, solution.endCoordinates.y)

        // "SUND" is at row 13
        solution = solver.findWord("SUND")
        assertNotNull(solution)
        assertEquals(WordDirection.HORIZONTAL, solution.direction)
        assertEquals(4, solution.startCoordinates.x)
        assertEquals(7, solution.endCoordinates.x)
        assertEquals(13, solution.startCoordinates.y)
        assertEquals(13, solution.endCoordinates.y)


        // REVERSED HORIZONTAL WORDS
        // Words: MOPSEN

        // MOPSEN is reversed at row 1
        // reversed words have start/end also reversed
        solution = solver.findWord("MOPSEN")
        assertNotNull(solution)
        assertEquals(WordDirection.HORIZONTAL_REVERSE, solution.direction)
        assertEquals(6, solution.startCoordinates.x)
        assertEquals(1, solution.endCoordinates.x)
        assertEquals(1, solution.startCoordinates.y)
        assertEquals(1, solution.endCoordinates.y)


        // VERTICAL WORDS
        // Words: BERME

        // BERME is at column 13
        solution = solver.findWord("BERME")
        assertNotNull(solution)
        assertEquals(WordDirection.VERTICAL, solution.direction)
        assertEquals(13, solution.startCoordinates.x)
        assertEquals(13, solution.endCoordinates.x)
        assertEquals(4, solution.startCoordinates.y)
        assertEquals(8, solution.endCoordinates.y)


        // REVERSED VERTICAL WORDS
        // Words: TÅTA, JESUS, MORGEN

        // TÅTA is reversed at column 0
        solution = solver.findWord("TÅTA")
        assertNotNull(solution)
        assertEquals(WordDirection.VERTICAL, solution.direction)
        assertEquals(0, solution.startCoordinates.x)
        assertEquals(0, solution.endCoordinates.x)
        assertEquals(3, solution.startCoordinates.y)
        assertEquals(6, solution.endCoordinates.y)

        // JESUS is reversed at column 7
        solution = solver.findWord("JESUS")
        assertNotNull(solution)
        assertEquals(WordDirection.VERTICAL, solution.direction)
        assertEquals(7, solution.startCoordinates.x)
        assertEquals(7, solution.endCoordinates.x)
        assertEquals(1, solution.startCoordinates.y)
        assertEquals(5, solution.endCoordinates.y)

        // MORGEN is reversed at column 2
        solution = solver.findWord("MORGEN")
        assertNotNull(solution)
        assertEquals(WordDirection.VERTICAL, solution.direction)
        assertEquals(2, solution.startCoordinates.x)
        assertEquals(2, solution.endCoordinates.x)
        assertEquals(7, solution.startCoordinates.y)
        assertEquals(12, solution.endCoordinates.y)


        // DIAGONALLY UPWARDS WORDS
        // Words: SVENNEN, JFR

        // SVENNEN is from column 2 to column 8, row 9 to row 3
        solution = solver.findWord("SVENNEN")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP, solution.direction)
        assertEquals(8, solution.startCoordinates.x)
        assertEquals(2, solution.endCoordinates.x)
        assertEquals(9, solution.startCoordinates.y)
        assertEquals(3, solution.endCoordinates.y)

        // JFR is from column 3 to column 5, row 12 to row 10
        solution = solver.findWord("JFR")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP, solution.direction)
        assertEquals(3, solution.startCoordinates.x)
        assertEquals(5, solution.endCoordinates.x)
        assertEquals(12, solution.startCoordinates.y)
        assertEquals(10, solution.endCoordinates.y)


        // DIAGONALLY UPWARDS REVERSED WORDS
        // Words: FORDØMT

        // FORDØMT is from column 8 to column 2, row 8 to row 2
        solution = solver.findWord("FORDØMT")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP_REVERSE, solution.direction)
        assertEquals(8, solution.startCoordinates.x)
        assertEquals(2, solution.endCoordinates.x)
        assertEquals(2, solution.startCoordinates.y)
        assertEquals(8, solution.endCoordinates.y)


        // DIAGONALLY DOWNWARDS WORDS
        // Words: SÆREGEN, STILTE, SVEIS

        // SÆREGEN is from column 2 to column 8, row 4 to row 10
        solution = solver.findWord("SÆREGEN")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN, solution.direction)
        assertEquals(2, solution.startCoordinates.x)
        assertEquals(8, solution.endCoordinates.x)
        assertEquals(4, solution.startCoordinates.y)
        assertEquals(10, solution.endCoordinates.y)

        // STILTE is from column 7 to column 12, row 3 to row 8
        solution = solver.findWord("STILTE")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN, solution.direction)
        assertEquals(7, solution.startCoordinates.x)
        assertEquals(12, solution.endCoordinates.x)
        assertEquals(3, solution.startCoordinates.y)
        assertEquals(8, solution.endCoordinates.y)

        // SVEIS is from column 6 to column 10, row 9 to row 13
        solution = solver.findWord("SVEIS")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN, solution.direction)
        assertEquals(6, solution.startCoordinates.x)
        assertEquals(10, solution.endCoordinates.x)
        assertEquals(9, solution.startCoordinates.y)
        assertEquals(13, solution.endCoordinates.y)


        // DIAGONALLY DOWNWARDS REVERSED WORDS
        // Words: AGNOSTIKER

        // AGNOSTIKER is from column 11 to column 2, row 2 to row 11
        solution = solver.findWord("AGNOSTIKER")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN_REVERSE, solution.direction)
        assertEquals(11, solution.startCoordinates.x)
        assertEquals(2, solution.endCoordinates.x)
        assertEquals(2, solution.startCoordinates.y)
        assertEquals(11, solution.endCoordinates.y)

    }


    @Test
    fun testNonValidWords() {
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

        var solution: SoupWordSolution?

        solution = solver.findWord("BRIKKET")
        assertNull(solution)

        solution = solver.findWord("MOBIL")
        assertNull(solution)

        // "ØD" is on the board
        solution = solver.findWord("ØDE")
        assertNull(solution)

        solution = solver.findWord("BRIKKET")
        assertNull(solution)
    }

}