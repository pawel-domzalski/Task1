package com.task1.pubsub

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PubSub {
    private val rxBus = PublishSubject.create<PubSubEvent>()

    fun send(event: PubSubEvent) {
        rxBus.onNext(event)
    }

    fun <EVENT : PubSubEvent> toObservable(clazz: Class<EVENT>): Observable<EVENT> {
        return rxBus.ofType(clazz)
    }
}

interface PubSubEvent

class MessageEvent (val message : String) : PubSubEvent
class SensorEvent (val value : Float) : PubSubEvent
class ObjectEvent (val obj : Any) : PubSubEvent