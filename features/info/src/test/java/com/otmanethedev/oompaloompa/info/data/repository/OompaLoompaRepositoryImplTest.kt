package com.otmanethedev.oompaloompa.info.data.repository

import com.otmanethedev.oompaloompa.info.data.datasource.RemoteDataSource
import com.otmanethedev.oompaloompa.info.data.models.OompaLoompaDto
import com.otmanethedev.oompaloompa.info.data.models.OompaLoompasListDto
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompasInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OompaLoompaRepositoryImplTest {

    private lateinit var repository: OompaLoompaRepositoryImpl

    private val remoteDataSource: RemoteDataSource = mockk()

    @Before
    fun setUp() {
        repository = OompaLoompaRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `given repository when call getOompaLoompasByPage then call remoteDataSource and return - success`() {
        runBlocking {
            // Given
            val dtoResult = OompaLoompasListDto(1, listOf(), 2)

            coEvery { remoteDataSource.getOompaLoompasByPage(1) } returns dtoResult

            // When
            val result = repository.getOompaLoompasByPage(1)

            // Then
            assertTrue(result.isSuccess)
            result.onSuccess {
                assertEquals(2, it.total)
                assertEquals(1, it.current)
                assertEquals(0, it.oompaLoompas.size)
            }
        }
    }

    @Test
    fun `given repository when call getOompaLoompasByPage then call remoteDataSource and return - failure`() {
        runBlocking {
            // Given
            val exception = mockk<Exception>()

            coEvery { remoteDataSource.getOompaLoompasByPage(1) } throws exception

            // When
            val result = repository.getOompaLoompasByPage(1)

            // Then
            assertEquals(Result.failure<OompaLoompasInfo>(exception), result)
        }
    }

    @Test
    fun `given repository when call getOompaLoompasById then call remoteDataSource and return - success`() {
        runBlocking {
            // Given
            val dtoResult = OompaLoompaDto(
                1,
                21,
                "Ulán bator",
                "randomEmail@oompaloompa.mn",
                null,
                "Alfredo",
                "Miquelet",
                "fluid",
                7,
                "",
                "miner",
                "",
                ""
            )

            coEvery { remoteDataSource.getOompaLoompaById(1) } returns dtoResult

            // When
            val result = repository.getOompaLoompaById(1)

            // Then
            assertTrue(result.isSuccess)
            result.onSuccess {
                assertEquals(1, it.id)
                assertEquals(21, it.age)
                assertEquals("Ulán bator", it.country)
            }
        }
    }

    @Test
    fun `given repository when call getOompaLoompasById then call remoteDataSource and return - failure`() {
        runBlocking {
            // Given
            val id = 1
            val exception = mockk<Exception>()

            coEvery { remoteDataSource.getOompaLoompaById(id) } throws exception

            // When
            val result = repository.getOompaLoompaById(id)

            // Then
            assertEquals(Result.failure<OompaLoompa>(exception), result)
        }
    }
}
