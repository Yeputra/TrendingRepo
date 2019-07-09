package id.gojek.trendingrepo

import id.gojek.trendingrepo.api.ApiRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {
    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://github-trending-api.now.sh/repositories"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}