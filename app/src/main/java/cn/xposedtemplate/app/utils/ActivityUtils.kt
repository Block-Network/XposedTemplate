/*
 * StatusBarLyric
 * Copyright (C) 2021-2022 fkj@fkj233.cn
 * https://github.com/577fkj/StatusBarLyric
 *
 * This software is free opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by 577fkj.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/577fkj/StatusBarLyric/blob/main/LICENSE>.
 */

package cn.xposedtemplate.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import cn.xposedtemplate.app.R
import cn.xposedtemplate.app.config.Config
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object ActivityUtils {
    //清除配置
    @JvmStatic
    fun cleanConfig(activity: Activity) {
        for (name in arrayOf("Lyric_Config")) {
            Utils.getSP(activity, name)?.let { Config(it) }?.clear()
        }
        LogUtils.toast(activity, activity.getString(R.string.ResetSuccess))
        activity.finishActivity(0)
    }

    fun openUrl(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun getHttp(Url: String): String {
        try {
            val connection = URL(Url).openConnection() as java.net.HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            return reader.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}