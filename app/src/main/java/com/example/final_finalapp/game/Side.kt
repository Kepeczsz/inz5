package com.example.final_finalapp.game

enum class Side {
    WHITE,

    BLACK,

    NEUTRAL;

    fun value(): String {
        return name
    }

    fun flip(): Side {
        return if (WHITE == this) BLACK else WHITE
    }

    companion object {
        var allSides: Array<Side> = Side.values()

        fun fromValue(v: String): Side {
            return Side.valueOf(v)
        }
    }
}