package io.well.schoccer.domain

data class Match(
    val time: String,
    val teams: String,
    val channels : String
) {
    override fun toString() = "$time -- $teams -- $channels"
}