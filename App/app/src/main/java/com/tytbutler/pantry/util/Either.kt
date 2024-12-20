package com.tytbutler.pantry.util

sealed class Either<A,B> {
    class Left<A,B>(val left: A): Either<A,B>()
    class Right<A,B>(val right: B): Either<A,B>()
}