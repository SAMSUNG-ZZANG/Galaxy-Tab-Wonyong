package com.example.sopt_seminar.domain.state

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     *  이벤트가 이미 처리 되었다면
     *  null 을 반환하고
     *  그렇지 않으면
     *  이벤트가 처리되었다고 표시한 후에
     *  값을 반환한다.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * 이벤트의 처리 여부에 상관 없이 값을 반환한다.
     */
    fun peekContent(): T = content
}