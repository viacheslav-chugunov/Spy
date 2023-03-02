package viacheslav.chugunov.spy.internal.presentation

import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsAdapter

class ConditionViewTypeAdapter {

    val map = mutableMapOf(
        SpyEventsAdapter.ViewType.SUCCESS to true,
        SpyEventsAdapter.ViewType.INFO to true,
        SpyEventsAdapter.ViewType.WARNING to true,
        SpyEventsAdapter.ViewType.ERROR to true
    )

    fun addCondition(key: Int, condition: Boolean) {
        map[key] = condition
    }

    fun get(value: Int): Boolean {
        return map[value]!!
    }
}