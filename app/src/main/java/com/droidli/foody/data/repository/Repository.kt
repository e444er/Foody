package com.droidli.foody.data.repository

import com.droidli.foody.data.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource
}