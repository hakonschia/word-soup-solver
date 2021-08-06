package soup

import util.Coordinates

data class SoupWordSolution(
    val word: String,
    val startCoordinates: Coordinates,
    val endCoordinates: Coordinates,
    val direction: WordDirection
)

/**
 * The valid directions a word can be found in the soup
 */
enum class WordDirection {
    HORIZONTAL,
    HORIZONTAL_REVERSE,
    VERTICAL,
    VERTICAL_REVERSE,
    DIAGONAL_DOWN,
    DIAGONAL_DOWN_REVERSE,
    DIAGONAL_UP,
    DIAGONAL_UP_REVERSE
}
