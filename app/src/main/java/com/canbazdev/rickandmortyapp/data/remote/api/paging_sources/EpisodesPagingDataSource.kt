package com.canbazdev.rickandmortyapp.data.remote.api.paging_sources

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.canbazdev.rickandmortyapp.data.model.episodes.Episode
import com.canbazdev.rickandmortyapp.data.remote.api.RickAndMortyService
import com.canbazdev.rickandmortyapp.util.Constants.STARTING_PAGE_INDEX
import javax.inject.Inject

/*
*   Created by hamzacanbaz on 12.07.2022
*/

class EpisodesPagingDataSource @Inject constructor(
    private val rickyAndMortyApi: RickAndMortyService
) : PagingSource<Int, Episode>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                rickyAndMortyApi.getEpisodes(
                    page = pageNumber
                )

            val data = response.results.map {
                it
            }

            var nextPageNumber: Int? = null

            val uri = Uri.parse(response.info.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()

            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return null
    }


}