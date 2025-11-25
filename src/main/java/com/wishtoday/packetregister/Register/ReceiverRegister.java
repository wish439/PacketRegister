package com.wishtoday.packetregister.Register;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import net.fabricmc.api.EnvType;
import net.minecraft.network.packet.CustomPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public interface ReceiverRegister {
    void register(CustomPayload.Id<CustomPayload> customPayload, Method method, Class<?> clazz);
    EnvType getEnvType();
    default void register(PacketClassInfo<CustomPayload> info) {
        Logger log = LoggerFactory.getLogger(this.getClass());
        if (info.hasEmpty()) {
            log.error("Empty PacketClassInfo");
            return;
        }
        register(info.getID(), info.getHANDLER(), info.getClazz());
    }
}
