package com.kwon.mywidgetcollection.db

import android.content.Context

class SharedDataBase(context: Context, prefAppName: String?): SharedPreferencesPropertyBackend(context, prefAppName) {
    override fun getString(key: String?, defaultValue: String?): String {
        return super.getString(key, defaultValue)
    }

    override fun setString(key: String?, value: String?): SharedPreferencesPropertyBackend {
        return super.setString(key, value)
    }

    override fun getInt(key: String?, defaultValue: Int): Int {
        return super.getInt(key, defaultValue)
    }

    override fun setInt(key: String?, value: Int): SharedPreferencesPropertyBackend {
        return super.setInt(key, value)
    }

    fun isAutoLogin(): Boolean {
        return false
    }
}