var isLogoShown = false
fun main(args: Array<String>) {
    val logo = """
.------.            _     _            _    _            _    
|A_  _ |.          | |   | |          | |  (_)          | |   
|( \/ ).-----.     | |__ | | __ _  ___| | ___  __ _  ___| | __
| \  /|K /\  |     | '_ \| |/ _` |/ __| |/ / |/ _` |/ __| |/ /
|  \/ | /  \ |     | |_) | | (_| | (__|   <| | (_| | (__|   < 
`-----| \  / |     |_.__/|_|\__,_|\___|_|\_\ |\__,_|\___|_|\_\\
      |  \/ K|                            _/ |                
      `------'                           |__/           
"""
    if (!isLogoShown) {
        println(logo)
        isLogoShown = true
    }
    fun compareScores() {
        if (dealerScore > 21) {
            println("Player won, ")
            return
        } else {
            when {
                dealerScore == userScore -> {
                    println("Draw")
                    return
                }
                dealerScore > userScore -> {
                    println("Lost")
                    return
                }
                dealerScore < userScore -> {
                    println("Won")
                    return
                }
            }
        }
    }

    fun game() {


        for (i in 0 until 2) {
            userCards.dealCards(cards, true)
            dealerCards.dealCards(cards)
        }
        calculateScores()
        println("Dealer : [${dealerCards.first()}, _ ]")

        while (true) {
            print("Do you want to continue: y/n ")
            val answer = readLine() ?: "n"
            if (answer.toLowerCase().startsWith('y')) {
                userCards.dealCards(cards, true)
                calculateScores()
            } else break
        }
        println("Dealer cards: $dealerCards")
        if (dealerScore >= 17) compareScores()
        while (dealerScore < 17) {
            dealerCards.dealCards(cards, true, "Dealer ")
            dealerScore = dealerCards.getScore()
            compareScores()
        }
    }
    resetScores()
    game()
    print("Do you wanna try again? ")
    val doYou = readLine() ?: "n"
    if (doYou.toLowerCase().startsWith('y')) {
        main(args)
    } else return
}

val cards = listOf(11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10)
val userCards = mutableListOf<Int>()
val dealerCards = mutableListOf<Int>()
var userScore = 0
var dealerScore = 0

private fun resetScores() {
    userCards.clear()
    dealerCards.clear()
    userScore = 0
    dealerScore = 0
}

private fun calculateScores() {
    userScore = userCards.getScore()
    dealerScore = dealerCards.getScore()
    println("UserScore $userScore , DealerScore $dealerScore")
    if (dealerScore == 21) {
        println("User lost, Dealer won")
        return
    }
    if (userScore == 21) {
        println("User won")
        return
    }
    if (userScore > 21) {
        if (userCards.contains(11)) {
            val index = userCards.indexOf(11)
            userCards[index] = 1
            userScore = userCards.getScore()
            println("UserScore $userScore , DealerScore $dealerScore")
            if (userScore > 21) {
                println("User lost")
                return
            }
        } else {
            println("User lost")
            return
        }
    }
    return
}

fun List<Int>.getScore() = sum()
fun MutableList<Int>.dealCards(cards: List<Int>, facing: Boolean = false, title: String = "Your cards") {
    add(cards.random()).run { if (facing) println("$title ${this@dealCards}") }
}