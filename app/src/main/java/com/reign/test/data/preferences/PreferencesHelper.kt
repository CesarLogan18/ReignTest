package com.reign.test.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private var sharedPreference: SharedPreferences =
        context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)

    fun setLastUpdateTime(time: Long) {
        var editor = sharedPreference.edit()
        editor.putLong("lastUpdate", time)
        editor.apply()
    }

    fun getLastUpdate(): Long {
        return sharedPreference.getLong("lastUpdate", 0L)
    }
}