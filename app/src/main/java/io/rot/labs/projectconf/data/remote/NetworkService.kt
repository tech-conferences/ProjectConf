package io.rot.labs.projectconf.data.remote

import io.reactivex.Single
import io.rot.labs.projectconf.data.model.Event
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.format.SignStyle
import javax.inject.Singleton


@Singleton
interface NetworkService {

   @GET("{year}/${EndPoints.ANDROID}")
   fun getAndroidEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.CLOJURE}")
   fun getClojureEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.CPP}")
   fun getCppEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.CSS}")
   fun getCssEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.DATA}")
   fun getDataEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.DEVOPS}")
   fun getDevOpsEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.DOTNET}")
   fun getDotNetEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.ELIXIR}")
   fun getElixirEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.ELM}")
   fun getElmEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.GENERAL}")
   fun getGeneralEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.GOLANG}")
   fun getGoLangEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.GRAPHQL}")
   fun getGraphqlEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.GROOVY}")
   fun getGroovyEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.IOS}")
   fun getIosEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.IOT}")
   fun getIotEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.JAVA}")
   fun getJavaEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.JAVASCRIPT}")
   fun getJavaScriptEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.KOTLIN}")
   fun getKotlinEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.LEADERSHIP}")
   fun getLeadershipEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.NETWORKING}")
   fun getNetworkingEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.PHP}")
   fun getPHPEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.PRODUCT}")
   fun getProductEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.PYTHON}")
   fun getPythonEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.RUBY}")
   fun getRubyEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.RUST}")
   fun getRustEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.SCALA}")
   fun getScalaEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.SECURITY}")
   fun getSecurityEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.TECH_COMM}")
   fun getTechCommEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.TYPESCRIPT}")
   fun getTypeScriptEventsByYear(@Path("year") year:Int):Single<List<Event>>

   @GET("{year}/${EndPoints.UX}")
   fun getUXEventsByYear(@Path("year") year:Int):Single<List<Event>>

}