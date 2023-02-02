package io.well.schoccer.domain

data class Schedule(
    val time: String,
    val matches: List<String>
) {
    override fun toString() = "$time\n${matches.joinToString("\n")}\n"
}
