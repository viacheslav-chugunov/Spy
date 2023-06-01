package viacheslav.chugunov.spy.internal.presentation.customview


private typealias Task = (listener: ToolbarView.Callback) -> Unit

class ToolbarActionsExecutor {

    private var listener: ToolbarView.Callback? = null

    private val tasks = mutableListOf<Task>()

    fun registerCallback(callback: ToolbarView.Callback) {
        this.listener = callback
        notifyNewListener(callback)
    }

    fun unregisterCallback(callback: ToolbarView.Callback) {
        val listener = this.listener
        if(listener==callback){
            this.listener = null
        }
    }

    fun executeTask( task: Task ) {
        val listener = this.listener
        if(listener == null) {
            tasks.add(task)
        } else {
            task.invoke(listener)
        }
    }

    private fun notifyNewListener(listener: ToolbarView.Callback) {
        tasks.forEach {
            it.invoke(listener)
        }
        tasks.clear()
    }

}