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

@file:Suppress("DEPRECATION")

package cn.xposedtemplate.app.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.SharedPreferences
import cn.xposedtemplate.app.BuildConfig
import de.robv.android.xposed.XSharedPreferences
import java.io.*
import java.util.*


@SuppressLint("StaticFieldLeak")
object Utils {
    @JvmStatic
    fun getPref(key: String?): XSharedPreferences? {
        val pref = XSharedPreferences(BuildConfig.APPLICATION_ID, key)
        return if (pref.file.canRead()) pref else null
    }

    @SuppressLint("WorldReadableFiles")
    @Suppress("DEPRECATION")
    @JvmStatic
    fun getSP(context: Context, key: String?): SharedPreferences? {
        return context.createDeviceProtectedStorageContext().getSharedPreferences(key, Context.MODE_WORLD_READABLE)
    }

     // 判断class是否存在
    @JvmStatic
    fun isPresent(name: String): Boolean {
        return try {
            Objects.requireNonNull(Thread.currentThread().contextClassLoader).loadClass(name)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    // 报错转内容
    @JvmStatic
    fun dumpException(e: Exception): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        return sw.toString()
    }

    @JvmStatic
    fun dumpIOException(e: IOException): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        return sw.toString()
    }

    @JvmStatic
    fun dumpNoSuchFieldError(e: NoSuchFieldError): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        return sw.toString()
    }

    @JvmStatic
    fun stringsListAdd(strArr: Array<String?>, newStr: String): Array<String?> {
        val newStrArr = arrayOfNulls<String>(strArr.size + 1)
        System.arraycopy(strArr, 0, newStrArr, 0, strArr.size)
        newStrArr[strArr.size] = newStr
        return newStrArr
    }

    @JvmStatic
    fun isServiceRunningList(context: Context, str: Array<String?>): Boolean {
        for (mStr in str) {
            if (mStr != null) {
                if (isAppRunning(context, mStr)) {
                    return true
                }
            }
        }
        return false
    }

    // 判断程序是否运行
    @JvmStatic
    fun isAppRunning(context: Context, str: String): Boolean {
        if (isServiceRunning(context, str)) {
            return true
        }
        val runningServices =
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getRunningTasks(200)
        if (runningServices.size <= 0) {
            return false
        }
        for (runningServiceInfo in runningServices) {
            if (runningServiceInfo.baseActivity!!.className.contains(str)) {
                LogUtils.e("程序运行: $str")
                return true
            }
        }
        return false
    }

    // 判断服务是否运行
    @JvmStatic
    fun isServiceRunning(context: Context, str: String): Boolean {
        val runningServices =
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getRunningServices(200)
        if (runningServices.size <= 0) {
            return false
        }
        for (runningServiceInfo in runningServices) {
            if (runningServiceInfo.service.className.contains(str)) {
                LogUtils.e("服务运行: $str")
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun voidShell(command: String, isSu: Boolean) {
        try {
            if (isSu) {
                val p = Runtime.getRuntime().exec("su")
                val outputStream = p.outputStream
                val dataOutputStream = DataOutputStream(outputStream)
                dataOutputStream.writeBytes(command)
                dataOutputStream.flush()
                dataOutputStream.close()
                outputStream.close()
            } else {
                Runtime.getRuntime().exec(command)
            }
        } catch (ignored: Throwable) {
        }
    }
}
