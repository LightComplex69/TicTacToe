package wpics.extendedtictactoe.models

/**
 * This class is an implementation of [IGameBoard] that uses a 2D array to store the information.
 *
 * @version 1.0
 */
class GameBoard(
    override val numRows: Int,
    override val numColumns: Int,
    override val numToWin: Int,

    // initialize info array
    var boardInfo: Array<Array<Char>> = Array(numRows) { Array(numColumns) { ' ' } }
) : AbsGameBoard(), IGameBoard {
    override fun isPlayerAtPos(pos: BoardPosition, player: Char): Boolean {
        return boardInfo[pos.row][pos.column] == player
    }

    override fun checkForWinner(lastPos: BoardPosition): Boolean {
        val lastChar = whatsAtPos(lastPos)
        return checkHorizontalWin(lastPos, lastChar) ||
                checkVerticalWin(lastPos, lastChar) ||
                checkDiagonalWin(lastPos, lastChar)
    }

    override fun whatsAtPos(pos: BoardPosition): Char {
        return boardInfo[pos.row][pos.column]
    }

    override fun placeMarker(marker: BoardPosition, player: Char) {
        if (checkSpace(marker)) boardInfo[marker.row][marker.column] = player
    }

    override fun checkSpace(pos: BoardPosition): Boolean {
        return if (pos.row in 0 until numRows &&
            pos.column in 0 until numColumns) {
            boardInfo[pos.row][pos.column] == ' '
        } else false
    }

    override fun checkHorizontalWin(lastPos: BoardPosition, player: Char): Boolean {
        var leftEdge = false    // bool for left edge
        var rightEdge = false   // bool for right edge
        var total = 1           // total Chars matching player
        var offset = 1          // board iterator

        // While neither edge has been reached.
        while (!(leftEdge && rightEdge)) {
            // Identify characters to left of last placed position.
            // If column doesn't exist or left edge reached, set to empty Char.
            val leftChar = if (lastPos.column - offset >= 0 && !leftEdge) {
                boardInfo[lastPos.row][lastPos.column - offset]
            } else ' '

            // Identify characters to right of last placed position.
            // If column doesn't exist or right edge reached, set to empty Char.
            val rightChar = if (lastPos.column + offset < numColumns && !rightEdge) {
                boardInfo[lastPos.row][lastPos.column + offset]
            } else ' '

            // Check if either character matches the player's character.
            // If not, consider respective edges reached.
            if (leftChar != player) leftEdge = true else total++
            if (rightChar != player) rightEdge = true else total++

            // If num to win reached, break loop.
            if (total >= numToWin) return true

            // Else increase offset.
            offset++
        }

        // If here, sufficient total wasn't reached.
        return false
    }

    override fun checkVerticalWin(lastPos: BoardPosition, player: Char): Boolean {
        var leftEdge = false    // bool for left edge
        var rightEdge = false   // bool for right edge
        var total = 1           // total Chars matching player
        var offset = 1          // board iterator

        // While neither edge has been reached.
        while (!(leftEdge && rightEdge)) {
            // Identify characters to left of last placed position.
            // If row doesn't exist or left edge reached, set to empty Char.
            val leftChar = if (lastPos.row - offset >= 0 && !leftEdge) {
                boardInfo[lastPos.row - offset][lastPos.column]
            } else ' '

            // Identify characters to right of last placed position.
            // If row doesn't exist or right edge reached, set to empty Char.
            val rightChar = if (lastPos.row + offset < numRows && !rightEdge) {
                boardInfo[lastPos.row + offset][lastPos.column]
            } else ' '

            // Check if either character matches the player's character.
            // If not, consider respective edges reached.
            if (leftChar != player) leftEdge = true else total++
            if (rightChar != player) rightEdge = true else total++

            // If num to win reached, break loop.
            if (total >= numToWin) return true

            // Else increase offset.
            offset++
        }

        // If here, sufficient total wasn't reached.
        return false
    }

    override fun checkDiagonalWin(lastPos: BoardPosition, player: Char): Boolean {
        var topLeftEdge = false     // bool for top left edge
        var topRightEdge = false    // bool for top right edge
        var botLeftEdge = false     // bool for bottom left edge
        var botRightEdge = false    // bool for bottom right edge
        var total1 = 1               // total Chars for \ diagonal
        var total2 = 1              // total Chars for / diagonal
        var offset = 1              // board iterator

        // While neither edge has been reached.
        while (!(topLeftEdge && topRightEdge && botLeftEdge && botRightEdge)) {
            // Identify characters to top left of last placed position.
            // If row doesn't exist or top left edge reached, set to empty Char.
            val topLeftChar =
                if (lastPos.row - offset >= 0 && lastPos.column - offset >= 0 && !topLeftEdge) {
                    boardInfo[lastPos.row - offset][lastPos.column - offset]
                } else ' '

            // Identify characters to top right of last placed position.
            // If row doesn't exist or top right edge reached, set to empty Char.
            val topRightChar =
                if (lastPos.row - offset >= 0 && lastPos.column + offset < numColumns && !topRightEdge) {
                    boardInfo[lastPos.row - offset][lastPos.column + offset]
                } else ' '

            // Identify characters to bottom left of last placed position.
            // If row doesn't exist or bottom left edge reached, set to empty Char.
            val botLeftChar =
                if (lastPos.row + offset < numRows && lastPos.column - offset >= 0 && !botLeftEdge) {
                    boardInfo[lastPos.row + offset][lastPos.column - offset]
                } else ' '

            // Identify characters to bottom right of last placed position.
            // If row doesn't exist or bottom right edge reached, set to empty Char.
            val botRightChar =
                if (lastPos.row + offset < numRows && lastPos.column + offset < numColumns && !botRightEdge) {
                    boardInfo[lastPos.row + offset][lastPos.column + offset]
                } else ' '

            // Check if any character matches the player's character.
            // If not, consider respective edges reached.
            if (topLeftChar != player) topLeftEdge = true else total1++
            if (botRightChar != player) botRightEdge = true else total1++
            if (topRightChar != player) topRightEdge = true else total2++
            if (botLeftChar != player) botLeftEdge = true else total2++

            // If num to win reached, break loop.
            if (total1 >= numToWin || total2 >= numToWin) return true

            // Else increase offset.
            offset++
        }

        // If here, sufficient total wasn't reached.
        return false
    }

    override fun checkForDraw(): Boolean {
        return !boardInfo.any { row -> ' ' in row }
    }
}