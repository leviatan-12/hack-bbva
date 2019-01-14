package com.idemia.biosmart.base.utils

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object DisposableManager {
    private const val TAG = "DisposableManager"

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable) {
        Log.i(TAG, "add()")
        if(!disposable.isDisposed)
            compositeDisposable.add(disposable)
    }

    fun dispose() {
        Log.i(TAG, "dispose()")
        compositeDisposable.dispose()
    }

    fun clear() {
        Log.i(TAG, "clear()")
        compositeDisposable.clear()
    }
}