package com.example.findlostitemapp.domain.model

open class NavRoute(val path: String) {
    open fun buildArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    open fun buildArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }

    open fun buildQueryArgs(vararg args: Pair<String, String>): String {
        return buildString {
            append(path)
            append("?")
            args.forEachIndexed { index, arg ->
                append("${arg.first}=${arg.second}")
                if (index < args.size - 1) {
                    append("&")
                }
            }
        }
    }

    open fun buildQueryArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            append("?")
            args.forEachIndexed { index, arg ->
                append("${arg}={${arg}}")
                if (index < args.size - 1) {
                    append("&")
                }
            }
        }
    }
}