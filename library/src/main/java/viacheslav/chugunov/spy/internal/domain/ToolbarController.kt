package viacheslav.chugunov.spy.internal.domain

import viacheslav.chugunov.spy.internal.presentation.customview.ToolbarView

internal interface ToolbarController {
    fun setDataToolbar(message:String?=null, callback: ToolbarView.Callback?=null)
    fun hideToolbarActions()
}