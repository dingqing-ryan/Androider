package com.ryan.lib_net.network


import io.reactivex.Flowable
import io.reactivex.functions.Function


class AppException<T> : Function<ResultModel<T>, Flowable<T>> {
    override fun apply(model: ResultModel<T>): Flowable<T> {
        return if (model.errorCode != 200) {
            Flowable.error(HttpCodeException(model.errorCode, model.errorMsg))
        } else Flowable.just(model.data!!)
    }
}
