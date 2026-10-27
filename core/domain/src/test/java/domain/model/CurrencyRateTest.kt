package domain.model

import com.n1cks.core.domain.model.CurrencyRate
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CurrencyRateTest {
    private val yen = CurrencyRate("JPY", "Японская иена", nominal = 100, value = 62.5, previous = 60.0)

    @Test
    fun `rate is divided by nominal`() {
        assertEquals(0.625, yen.rubPerUnit, 1e-9)
    }

    @Test
    fun `conversion works both ways`() {
        assertEquals(125.0, yen.toRub(200.0), 1e-9)
        assertEquals(200.0, yen.fromRub(125.0), 1e-9)
    }

    @Test
    fun `change is difference to previous`() {
        assertEquals(2.5, yen.change, 1e-9)
    }
}