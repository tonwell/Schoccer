package io.well.schoccer.repository

import io.well.schoccer.datasource.DataSource
import io.well.schoccer.domain.Match
import io.well.schoccer.domain.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    private val ws = """[\s\u00A0]"""
    private val liRegex = """(\d{2}:\d{2}|\d{2}h\d{0,2}|\d{2} horas|\d{2}h\d{2}min)\s–\s(.*)?\s–\s(.*)""".toRegex()
    private val liRegexNoChannel = """(\d{2}:\d{2}|\d{2}h\d{0,2}|\d{2} horas|\d{2}h\d{2}min)\s–\s(.*)""".toRegex()
    private val pRegex = """(\d{2}:\d{2}|\d{2}h\d{0,2}|\d{2} horas|\d{2}h\d{2}min)\s(.*)?(\sRodada|\sSemifinal|\sFinal).*""".toRegex()
    private val pTagRegex = """<p>|</p>""".toRegex()
    private val listSelectorQuery = "article.category-onde-assistir div.entry-content li"
    private val pSelectorQuery = "article.category-onde-assistir div.entry-content h2 + p"

    private fun getGamesInfo(element: Element): List<String> {
        return element
            .toString()
            .replace(pTagRegex, "")
            .split("<br>")
            .map(String::trim)
    }

    private fun mapMatch(regex: Regex, textToMatch: String) : Match? {
        return regex.matchEntire(textToMatch)?.destructured?.let { (time, teams, channels) ->
            Match(time, teams, channels)
        }
    }


    private fun getMatchesByParagraph(elements: Elements): List<Match> {
        return elements.flatMap { element ->
            getGamesInfo(element).mapNotNull { gameInfo ->
                mapMatch(pRegex, gameInfo) ?: mapMatch(liRegex, gameInfo) ?: mapMatch(liRegexNoChannel, gameInfo)
            }
        }
    }

    private fun getMatchesByList(elements: Elements): List<Match> {
        return elements.mapNotNull { element ->
            mapMatch(liRegex, element.text()) ?: mapMatch(liRegexNoChannel, element.text())
        }
    }

    private fun getMatches(): Flow<List<Match>> {
        return DataSource.getData().map { document ->
            var elements = document.select(listSelectorQuery)
            if (elements.isNotEmpty()) {
                getMatchesByList(elements)
            } else {
                elements = document.select(pSelectorQuery)
                getMatchesByParagraph(elements)
            }
        }
    }

    override fun getSchedules(): Flow<List<Schedule>> {
        return getMatches().map { matches ->
            matches.sortedBy { it.time }.groupBy { it.time }.map { (time, matchGroup) ->
                Schedule(
                    time,
                    matchGroup.map { it.teams + if (it.channels.isNotEmpty()) " - " + it.channels else "" }
                )
            }
        }
    }
}