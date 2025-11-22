package com.wishtoday.packetregister.Util;

import lombok.Getter;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.objectweb.asm.Type;

public enum PacketState {
    S2C(ClientPlayNetworking.Context.class),
    C2S(ServerPlayNetworking.Context.class);
    @Getter
    private final Class<?> contextClass;
    @Getter
    private final String contextClassPath;
    PacketState(Class<?> contextClass) {
        this.contextClass = contextClass;
        this.contextClassPath = Type.getDescriptor(this.contextClass);
    }
}
