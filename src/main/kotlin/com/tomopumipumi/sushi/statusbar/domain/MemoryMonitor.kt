package com.tomopumipumi.sushi.statusbar.domain

object MemoryMonitor {
    private const val MEGABYTE = 1024L * 1024L
    private const val MB_PER_SUSHI = 250L
    private const val MAX_SUSHI = 15

    fun getUsedMemoryMB(): Long {
        val runtime = Runtime.getRuntime()
        return (runtime.totalMemory() - runtime.freeMemory()) / MEGABYTE
    }

    fun getTotalMemoryMB(): Long {
        return Runtime.getRuntime().totalMemory() / MEGABYTE
    }

    fun getMaxMemoryMB(): Long {
        return Runtime.getRuntime().maxMemory() / MEGABYTE
    }

    fun calculateSushiCount(): Int {
        val usedMB = getUsedMemoryMB()
        return (usedMB / MB_PER_SUSHI).toInt().coerceIn(1, MAX_SUSHI)
    }
}