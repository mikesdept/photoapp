package mikes.dept.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import mikes.dept.domain.entities.PhotoEntity
import kotlin.math.max

abstract class PhotoPagingSingleDataSource : PagingSource<Int, PhotoEntity>() {

    private companion object {
        private const val DEFAULT_START_PAGE = 0
        private const val ONE_PAGE = 1
    }

    abstract suspend fun getPhotos(currentPage: Int): List<PhotoEntity>

    override fun getRefreshKey(state: PagingState<Int, PhotoEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntity> {
        val currentPage = params.key ?: DEFAULT_START_PAGE

        val result = runCatching {
            Result.Success(data = getPhotos(currentPage = currentPage))
        }.getOrElse { throwable -> Result.Failure(throwable = throwable) }

        return when (result) {
            is Result.Failure -> LoadResult.Error(result.throwable)
            is Result.Success -> LoadResult.Page(
                data = result.data,
                prevKey = when (currentPage) {
                    DEFAULT_START_PAGE -> null
                    else -> max(DEFAULT_START_PAGE, currentPage - ONE_PAGE)
                },
                nextKey = currentPage + ONE_PAGE
            )
        }
    }

    private sealed class Result {

        data class Success(val data: List<PhotoEntity>): Result()

        data class Failure(val throwable: Throwable): Result()

    }

}
