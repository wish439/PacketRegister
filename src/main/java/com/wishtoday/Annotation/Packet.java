package com.wishtoday.Annotation;

import com.wishtoday.packetregister.Util.PacketState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Packet {
    PacketState value();
}
