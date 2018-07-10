package com.summer.itis.summerproject.api


import com.summer.itis.summerproject.BuildConfig

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class ApiFactory {

    companion object {
        @JvmField
        var wikiService: WikiService? = null

        @JvmStatic fun getWikiService(): WikiService? {
            if (wikiService == null) {
                synchronized(ApiFactory::class.java) {
                    if (wikiService == null) {
                        wikiService = buildRetrofit().create(WikiService::class.java)
                    }
                }
            }
            return wikiService
        }

        fun recreate() {
            OkHttpProvider.recreate()
            this.wikiService = buildRetrofit().create(WikiService::class.java)
        }

        private fun buildRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .client(OkHttpProvider.provideClient())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }

}
