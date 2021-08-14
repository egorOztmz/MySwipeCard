package com.oztmzegor.myswipecard.util

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resId : Int) :String
}