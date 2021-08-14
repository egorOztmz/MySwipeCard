package com.oztmzegor.myswipecard.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
    ) : ResourceProvider {

    override fun getString(@StringRes resId : Int) =
        context.getString(resId)

}