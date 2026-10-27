package com.n1cks.core.data.mapper

import com.n1cks.core.data.remote.cbr.CbrJson
import com.n1cks.core.data.remote.mapper.toDomain
import com.n1cks.core.data.remote.response.RatesResponse
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.Instant

class RatesMapperTest {

    private val sample = """
        {
          "Date": "2026-10-27T11:30:00+03:00",
          "PreviousDate": "2026-10-24T11:30:00+03:00",
          "Valute": {
            "JPY": {"ID": "R01820", "NumCode": "392", "CharCode": "JPY", "Nominal": 100,
                    "Name": "Японских иен", "Value": 62.5, "Previous": 60.0},
            "USD": {"ID": "R01235", "NumCode": "840", "CharCode": "USD", "Nominal": 1,
                    "Name": "Доллар США", "Value": 95.1, "Previous": 94.8}
          }
        }
    """.trimIndent()

    @Test
    fun `response is mapped to domain snapshot`() {
        val snapshot = CbrJson.decodeFromString<RatesResponse>(sample).toDomain()

        assertEquals(listOf("JPY", "USD"), snapshot.rates.map { it.code })
        assertEquals(100, snapshot.rates.first().nominal)
        assertEquals(Instant.parse("2026-10-27T08:30:00Z"), snapshot.publishedAt)
    }
}