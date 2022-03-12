package cn.xposedtemplate.app.hook

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

abstract class BaseHook(lpparam: LoadPackageParam) {
    init {
    }

    abstract fun hook()
}