package com.wishtoday.packetregister.client;

import com.wishtoday.packetregister.Register.ReceiverRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientReceiverRegister implements ReceiverRegister {
    EnvType type;
    public ClientReceiverRegister() {
        this.type = EnvType.CLIENT;
    }
    @Override
    public void register(CustomPayload.Id<CustomPayload> customPayload, Method method, Class<?> clazz) {
        ClientPlayNetworking.registerGlobalReceiver(customPayload, (payload, context) -> {
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
