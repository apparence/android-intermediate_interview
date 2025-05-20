package com.onespan.android.interview.retrofit

import com.onespan.android.interview.model.dto.Breed
import com.onespan.android.interview.model.dto.Breeds
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class ApiServiceRepositoryTest {

    @MockK
    private lateinit var apiService: ApiService

    @InjectMockKs
    private lateinit var apiServiceImpl: ApiServiceRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `when getBreeds is called then returns Result Success`() {
        coEvery { apiService.getBreeds(20,1) } returns Response.success(Breeds(breeds = mutableListOf(
            Breed("breed", "coutry", "origin", "coat", "pattern")
        )))

        val result = runBlocking {
            apiServiceImpl.getBreeds(20,1)
        }

        assertEquals(1, result.body?.breeds?.size)
    }

    @Test
    fun `when getBreeds is called then return Result Error`() {
        coEvery { apiService.getBreeds(20,1) } returns Response.error(400, "{ code = 400, message =\"This is an error\"}".toResponseBody())

        val result = runBlocking {
            apiServiceImpl.getBreeds(20,1)
        }

        assertEquals("This is an error", result.errorResponse?.message)
    }

    @Test
    fun `when getBreeds is called then throw exception`() {
        coEvery { apiService.getBreeds(20,1) } throws Exception("Exception")

        val result = runBlocking {
            apiServiceImpl.getBreeds(20,1)
        }

        assertEquals("Exception", result.errorResponse?.message)
    }
}