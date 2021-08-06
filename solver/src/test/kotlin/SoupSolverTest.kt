package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import soup.SoupSolver
import soup.SoupWordSolution
import soup.WordDirection
import kotlin.test.*

internal class SoupSolverTest {

    /**
     * Tests that giving a non-square n*n board to the constructor of [SoupSolver] throws an error
     */
    @Test
    fun nonSquareBoardThrowsException() {
        assertThrows<IllegalStateException> {
            SoupSolver(
                arrayOf(
                    charArrayOf('A', 'B', 'C'),
                    charArrayOf('A', 'B')
                )
            )
        }
    }

    /**
     * Tests that giving a square n*n board to the constructor of [SoupSolver] does not throw an error
     */
    @Test
    fun squareBoardDoesNotThrowException() {
        SoupSolver(
            arrayOf(
                charArrayOf('A', 'B', 'C'),
                charArrayOf('A', 'B', 'C'),
                charArrayOf('A', 'B', 'D')
            )
        )
    }


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
        assertEquals(WordDirection.VERTICAL_REVERSE, solution.direction)
        assertEquals(0, solution.startCoordinates.x)
        assertEquals(0, solution.endCoordinates.x)
        assertEquals(6, solution.startCoordinates.y)
        assertEquals(3, solution.endCoordinates.y)

        // JESUS is reversed at column 7
        solution = solver.findWord("JESUS")
        assertNotNull(solution)
        assertEquals(WordDirection.VERTICAL_REVERSE, solution.direction)
        assertEquals(7, solution.startCoordinates.x)
        assertEquals(7, solution.endCoordinates.x)
        assertEquals(5, solution.startCoordinates.y)
        assertEquals(1, solution.endCoordinates.y)

        // MORGEN is reversed at column 2
        solution = solver.findWord("MORGEN")
        assertNotNull(solution)
        assertEquals(WordDirection.VERTICAL_REVERSE, solution.direction)
        assertEquals(1, solution.startCoordinates.x)
        assertEquals(1, solution.endCoordinates.x)
        assertEquals(11, solution.startCoordinates.y)
        assertEquals(6, solution.endCoordinates.y)


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

        // LNEN is from column 9 to column 6, row 2 to row 5
        // (not really a word in the board it is taken from, just a test case for a reversed diagonal column)
        solution = solver.findWord("LNEN")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN_REVERSE, solution.direction)
        assertEquals(9, solution.startCoordinates.x)
        assertEquals(6, solution.endCoordinates.x)
        assertEquals(2, solution.startCoordinates.y)
        assertEquals(5, solution.endCoordinates.y)

        // ENYT is from column 13 to column 10, row 8 to row 11
        // (not really a word in the board it is taken from, just a test case for a reversed diagonal row)
        solution = solver.findWord("ENYT")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN_REVERSE, solution.direction)
        assertEquals(13, solution.startCoordinates.x)
        assertEquals(10, solution.endCoordinates.x)
        assertEquals(8, solution.startCoordinates.y)
        assertEquals(11, solution.endCoordinates.y)

        // ÅSE is from column 11 to column 9, row 8 to row 11
        // (not really a word in the board it is taken from, just a test case for a reversed diagonal row)
        solution = solver.findWord("ÅSE")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN_REVERSE, solution.direction)
        assertEquals(11, solution.startCoordinates.x)
        assertEquals(9, solution.endCoordinates.x)
        assertEquals(8, solution.startCoordinates.y)
        assertEquals(10, solution.endCoordinates.y)

        // ÅSE is from column 9 to column 4, row 6 to row 11
        // (not really a word in the board it is taken from, just a test case for a reversed diagonal row)
        solution = solver.findWord("TWRSRF")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN_REVERSE, solution.direction)
        assertEquals(9, solution.startCoordinates.x)
        assertEquals(4, solution.endCoordinates.x)
        assertEquals(6, solution.startCoordinates.y)
        assertEquals(11, solution.endCoordinates.y)

        // AGNOSTIKER is from column 11 to column 2, row 2 to row 11
        solution = solver.findWord("AGNOSTIKER")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_DOWN_REVERSE, solution.direction)
        assertEquals(11, solution.startCoordinates.x)
        assertEquals(2, solution.endCoordinates.x)
        assertEquals(2, solution.startCoordinates.y)
        assertEquals(11, solution.endCoordinates.y)


        // DIAGONALLY UPWARDS WORDS
        // Words: SVENNEN, JFR

        // SVENNEN is from column 2 to column 8, row 9 to row 3
        solution = solver.findWord("SVENNEN")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP, solution.direction)
        assertEquals(2, solution.startCoordinates.x)
        assertEquals(8, solution.endCoordinates.x)
        assertEquals(9, solution.startCoordinates.y)
        assertEquals(3, solution.endCoordinates.y)

        // TCSM is from column 0 to column 3, row 6 to row 3
        // (not really a word in the board it is taken from, just a test case for an upwards diagonal row)
        solution = solver.findWord("TCSM")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP, solution.direction)
        assertEquals(0, solution.startCoordinates.x)
        assertEquals(3, solution.endCoordinates.x)
        assertEquals(6, solution.startCoordinates.y)
        assertEquals(3, solution.endCoordinates.y)

        // JFR is from column 3 to column 5, row 12 to row 10
        solution = solver.findWord("JFR")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP, solution.direction)
        assertEquals(3, solution.startCoordinates.x)
        assertEquals(5, solution.endCoordinates.x)
        assertEquals(12, solution.startCoordinates.y)
        assertEquals(10, solution.endCoordinates.y)

        // EREKIT is from column 1 to column 6, row 12 to row 7
        // (not really a word in the board it is taken from, just a test case for an upwards diagonal row)
        solution = solver.findWord("EREKIT")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP, solution.direction)
        assertEquals(1, solution.startCoordinates.x)
        assertEquals(6, solution.endCoordinates.x)
        assertEquals(12, solution.startCoordinates.y)
        assertEquals(7, solution.endCoordinates.y)


        // DIAGONALLY UPWARDS REVERSED WORDS
        // Words: FORDØMT

        // FORDØMT is from column 8 to column 2, row 8 to row 2
        solution = solver.findWord("FORDØMT")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP_REVERSE, solution.direction)
        assertEquals(8, solution.startCoordinates.x)
        assertEquals(2, solution.endCoordinates.x)
        assertEquals(8, solution.startCoordinates.y)
        assertEquals(2, solution.endCoordinates.y)

        // NNUM is from column 9 to column 6, row 4 to row 1
        // (not really a word in the board it is taken from, just a test case for a reversed upwards diagonal row)
        solution = solver.findWord("NNUM")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP_REVERSE, solution.direction)
        assertEquals(9, solution.startCoordinates.x)
        assertEquals(6, solution.endCoordinates.x)
        assertEquals(4, solution.startCoordinates.y)
        assertEquals(1, solution.endCoordinates.y)

        // NNUM is from column 6 to column 4, row 10 to row 8
        // (not really a word in the board it is taken from, just a test case for a reversed upwards diagonal row)
        solution = solver.findWord("PEN")
        assertNotNull(solution)
        assertEquals(WordDirection.DIAGONAL_UP_REVERSE, solution.direction)
        assertEquals(6, solution.startCoordinates.x)
        assertEquals(4, solution.endCoordinates.x)
        assertEquals(10, solution.startCoordinates.y)
        assertEquals(8, solution.endCoordinates.y)
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


    /**
     * Tests that a horizontal word produces the correct generated board
     */
    @Test
    fun generatedBoardHorizontalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        // ESL is at the top row
        solver.findWord("ESL")

        assertEquals(
            """
                E S L f v
                r n e s p
                e o t q w
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("TQW")
        assertEquals(
            """
                E S L f v
                r n e s p
                e o T Q W
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that a reverse horizontal word produces the correct generated board
     */
    @Test
    fun generatedBoardReverseHorizontalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("FLS")
        assertEquals(
            """
                e S L F v
                r n e s p
                e o t q w
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("FMI")
        assertEquals(
            """
                e S L F v
                r n e s p
                e o t q w
                a m I M F
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that a vertical word produces the correct generated board
     */
    @Test
    fun generatedBoardVerticalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("REA")
        assertEquals(
            """
                e s l f v
                R n e s p
                E o t q w
                A m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("WFØ")
        assertEquals(
            """
                e s l f v
                R n e s p
                E o t q W
                A m i m F
                t f s s Ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that a reverse vertical word produces the correct generated board
     */
    @Test
    fun generatedBoardReverseVerticalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("FMO")
        assertEquals(
            """
                e s l f v
                r n e s p
                e O t q w
                a M i m f
                t F s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("MQS")
        assertEquals(
            """
                e s l f v
                r n e S p
                e O t Q w
                a M i M f
                t F s s ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that a downwards diagonal word produces the correct generated board
     */
    @Test
    fun generatedBoardDownwardsDiagonalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("ENT")
        assertEquals(
            """
                E s l f v
                r N e s p
                e o T q w
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("TMØ")
        assertEquals(
            """
                E s l f v
                r N e s p
                e o T q w
                a m i M f
                t f s s Ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that a reverse downwards diagonal word produces the correct generated board
     */
    @Test
    fun generatedBoardReverseDownwardsDiagonalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("VST")
        assertEquals(
            """
                e s l f V
                r n e S p
                e o T q w
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("WMS")
        assertEquals(
            """
                e s l f V
                r n e S p
                e o T q W
                a m i M f
                t f S s ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that an upwards diagonal word produces the correct generated board
     */
    @Test
    fun generatedBoardUpwardsDiagonalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("TMT")
        assertEquals(
            """
                e s l f v
                r n e s p
                e o T q w
                a M i m f
                T f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("RS")
        assertEquals(
            """
                e S l f v
                R n e s p
                e o T q w
                a M i m f
                T f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    /**
     * Tests that a reverse upwards diagonal word produces the correct generated board
     */
    @Test
    fun generatedBoardReverseUpwardsDiagonalWord() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)

        solver.findWord("SIO")
        assertEquals(
            """
                e s l f v
                r n e s p
                e O t q w
                a m I m f
                t f s S ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("WSL")
        assertEquals(
            """
                e s L f v
                r n e S p
                e O t q W
                a m I m f
                t f s S ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }

    @Test
    fun boardGeneratedBySolverHighlightsSolvedWords() {
        val board = arrayOf(
            charArrayOf('E', 'S', 'L', 'F', 'V'),
            charArrayOf('R', 'N', 'E', 'S', 'P'),
            charArrayOf('E', 'O', 'T', 'Q', 'W'),
            charArrayOf('A', 'M', 'I', 'M', 'F'),
            charArrayOf('T', 'F', 'S', 'S', 'Ø'),
        )

        val solver = SoupSolver(board)


        // ESL is at the top row
        solver.findWord("ESL")
        assertEquals(
            """
                E S L f v
                r n e s p
                e o t q w
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        // The "L" in "LET" is part of "ESL", so this tests that the highlight of one character can be part of two words
        solver.findWord("LET")
        assertEquals(
            """
                E S L f v
                r n E s p
                e o T q w
                a m i m f
                t f s s ø
            """.trimIndent(),
            solver.generateBoard()
        )

        solver.findWord("ØMT")
        assertEquals(
            """
                E S L f v
                r n E s p
                e o T q w
                a m i M f
                t f s s Ø
            """.trimIndent(),
            solver.generateBoard()
        )
    }
}