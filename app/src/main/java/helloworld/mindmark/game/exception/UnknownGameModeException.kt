package helloworld.mindmark.game.exception

import java.lang.RuntimeException

class UnknownGameModeException(message: String) : RuntimeException(message) {
}