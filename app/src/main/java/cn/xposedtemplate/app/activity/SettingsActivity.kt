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

package cn.xposedtemplate.app.activity

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import cn.xposedtemplate.app.utils.ActivityOwnSP
import cn.xposedtemplate.app.utils.Utils
import cn.xposedtemplate.app.utils.LogUtils


class SettingsActivity : Activity() {
    private val activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityOwnSP.activity = activity
        checkLSPosed()
        LogUtils.toast(activity, "aaa")
    }

    private fun checkLSPosed(): Boolean {
        return try {
            Utils.getSP(activity, "Lyric_Config")?.let { }
            true
        } catch (e: Throwable) {
            AlertDialog.Builder(activity).apply {
                setMessage("未激活")
                setPositiveButton("退出") { _, _ ->
                    run {
                        finish()
                    }
                }
                create()
                show()
            }

            false
        }
    }
}
