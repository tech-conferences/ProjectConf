/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.data.remote

import io.reactivex.Single
import io.rot.labs.projectconf.data.model.Event
import javax.inject.Singleton
import retrofit2.http.GET
import retrofit2.http.Path

@Singleton
interface NetworkService {

    @GET("{year}/{tech}.json")
    fun getEventsByYear(@Path("year") year: Int, @Path("tech") tech: String): Single<List<Event>>
}
