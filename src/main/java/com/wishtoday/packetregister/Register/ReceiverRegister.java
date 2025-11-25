package com.wishtoday.packetregister.Register;

import net.fabricmc.api.EnvType;
import net.minecraft.network.packet.CustomPayload;

import java.lang.reflect.Method;

public interface ReceiverRegister {
    void register(CustomPayload.Id<CustomPayload> customPayload, Method method, Class<?> clazz);
    EnvType getEnvType();
}
