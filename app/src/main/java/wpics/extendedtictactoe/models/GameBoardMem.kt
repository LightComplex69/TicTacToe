package wpics.extendedtictactoe.models

/**
 * This class is an implementation of [IGameBoard] that uses a map of players (key) to
 * list of occupied positions (value) to store the information.
 *
 * @version 1.0
 */
class GameBoardMem(
    override val numRows: Int,
    override val numColumns: Int,
    override val numToWin: Int,

    // initialize info array
    var boardInfo: HashMap<Char, List<BoardPosition>> = HashMap<Char, List<BoardPosition>>()
) : AbsGameBoard(), IGameBoard {
    override fun isPlayerAtPos(pos: BoardPosition, player: Char): Boolean {
        return boardInfo[player]?.contains(pos) ?: false
    }
    override fun checkForWinner(lastPos: BoardPosition): Boolean {
        val lastChar = whatsAtPos(lastPos)
        return checkHorizontalWin(lastPos, lastChar) ||
                checkVerticalWin(lastPos, lastChar) ||
                checkDiagonalWin(lastPos, lastChar)
    }

    override fun placeMarker(marker: BoardPosition, player: Char) {
        boardInfo[player] = (boardInfo[player] ?: emptyList()) + marker
    }

    override fun checkSpace(pos: BoardPosition): Boolean {
        if (pos.row !in 0 until numRows ||
            pos.column !in 0 until numColumns) {
            return false
        }

        return whatsAtPos(pos) == ' '
    }

    override fun whatsAtPos(pos: BoardPosition): Char {
        for ((player, positions) in boardInfo) {
            if (positions.contains(pos)) {
                return player
            }
        }

        return ' '
    }

    override fun checkHorizontalWin(lastPos: BoardPosition, player: Char): Boolean {
        var total = 1
        var currCol = lastPos.column - 1

        while (currCol >= 0 &&
        whatsAtPos(BoardPosition(lastPos.row, currCol)) == player) {
            total++
            currCol--
        }

        currCol = lastPos.column + 1
        while (currCol < numColumns &&
        whatsAtPos(BoardPosition(lastPos.row, currCol)) == player) {
            total++
            currCol++
        }

        return total >= numToWin
    }

    override fun checkVerticalWin(lastPos: BoardPosition, player: Char): Boolean {
        var total = 1
        var currRow = lastPos.row - 1

        while (currRow >= 0 &&
            whatsAtPos(BoardPosition(currRow, lastPos.column)) == player) {
            total++
            currRow--
        }

        currRow = lastPos.row + 1
        while (currRow < numRows &&
            whatsAtPos(BoardPosition(currRow, lastPos.column)) == player) {
            total++
            currRow++
        }

        return total >= numToWin
    }

    override fun checkDiagonalWin(lastPos: BoardPosition, player: Char): Boolean {
        // check \ diagonal
        var total = 1
        var currRow = lastPos.row - 1
        var currColumn = lastPos.column - 1

        while (currRow >= 0 && currColumn >= 0 &&
        whatsAtPos(BoardPosition(currRow, currColumn)) == player){
            total++
            currRow--
            currColumn--
        }

        currRow = lastPos.row + 1
        currColumn = lastPos.column + 1

        while (currRow < numRows && currColumn < numColumns &&
        whatsAtPos(BoardPosition(currRow, currColumn)) == player) {
            total++
            currRow++
            currColumn++
        }

        if (total >= numToWin) {
            return true
        }

        // check / diagonal
        total = 1
        currRow = lastPos.row - 1
        currColumn = lastPos.column + 1

        while (currRow >= 0 && currColumn < numColumns &&
            whatsAtPos(BoardPosition(currRow, currColumn)) == player){
            total++
            currRow--
            currColumn++
        }

        currRow = lastPos.row + 1
        currColumn = lastPos.column - 1

        while (currRow < numRows && currColumn >= 0 &&
            whatsAtPos(BoardPosition(currRow, currColumn)) == player) {
            total++
            currRow++
            currColumn--
        }

        return total >= numToWin
    }

    override fun checkForDraw(): Boolean {
        var filledSpaces = 0

        for (positions in boardInfo.values) {
            filledSpaces += positions.size
        }

        return filledSpaces == numRows * numColumns
    }
}