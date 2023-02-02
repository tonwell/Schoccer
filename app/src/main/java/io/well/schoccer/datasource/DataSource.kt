package io.well.schoccer.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object DataSource {
    private const val URL = "https://www.minhatorcida.com.br/jogos-de-hoje-na-tv"

    fun getData(): Flow<Document> {
        return flow {
            val data = Jsoup.connect(URL).get()
            emit(data)
        }.flowOn(Dispatchers.IO)
    }
}