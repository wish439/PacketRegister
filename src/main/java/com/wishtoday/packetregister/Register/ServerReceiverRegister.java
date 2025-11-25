package com.wishtoday.packetregister.Register;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerReceiverRegister implements ReceiverRegister{
    EnvType type;
    public ServerReceiverRegister() {
        this.type = EnvType.SERVER;
    }
    @Override
    public void register(CustomPayload.Id<CustomPayload> customPayload
            , Method method, Class<?> clazz) {
        ServerPlayNetworking.registerGlobalReceiver(customPayload, (payload, context) -> {
            try {
                method.invoke(null,clazz.cast(payload), context);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public EnvType getEnvType() {
        return this.type;
    }
}
