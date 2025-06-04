package com.deliveryapp
import com.deliveryapp.deliverymodule.domain.model.createGlideLink
import com.yandex.mapkit.geometry.Point
import org.junit.Assert.assertEquals
import org.junit.Test
/**
 * Проверяет корректную генерацию функцией [createGlideLink] URL запроса
 * статической карты Яндекс на основе двух точек с координатами и заданного API-ключа
 */
class CreateGlideLinkTest {
    @Test
    fun `createGlideLink returns correct url`() {
        val from = Point(47.22663, 38.89492)
        val to = Point(47.227, 38.9)
        val url = createGlideLink(from, to, "TEST_API_KEY")
        val expected = "https://static-maps.yandex.ru/v1?&pt=38.89492,47.22663,round1~38.9,47.227,round2&size=300,300&apikey=TEST_API_KEY"
        assertEquals(expected, url)
    }
}
