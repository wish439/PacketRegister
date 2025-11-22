package com.wishtoday.packetregister.Register;

import com.wishtoday.Annotation.Initialize;
import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Util.PacketState;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

@Initialize
@Log4j2
public class PayloadRegister {
    static {
        register();
    }
    public static void register() {
        Collection<PacketClassInfo<CustomPayload>> info = PacketClassManager.getInstance()
                .getAllPacketClassInfo();
        for (PacketClassInfo<CustomPayload> classInfo : info) {
            if (classInfo.hasEmpty()) {
                log.warn("Packet ID{}has null or empty", classInfo.getID());
                continue;
            }
            chooseRegister(classInfo);
        }
    }
    private static void chooseRegister(PacketClassInfo<CustomPayload> info) {
        PacketState state = info.getState();
        switch (state) {
            case S2C -> S2CRegister(info);
            case C2S -> C2SRegister(info);
        }
    }
    private static void S2CRegister(PacketClassInfo<CustomPayload> info) {
        PayloadTypeRegistry.playS2C().register(info.getID(), info.getCODEC());
        ClientPlayNetworking.registerGlobalReceiver(info.getID(), (payload, context) -> {
            try {
                info.getHANDLER().invoke(null,info.getClazz().cast(payload), context);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private static void C2SRegister(PacketClassInfo<CustomPayload> info) {
        PayloadTypeRegistry.playC2S().register(info.getID(), info.getCODEC());
        ServerPlayNetworking.registerGlobalReceiver(info.getID(), (payload, context) -> {
            try {
                info.getHANDLER().invoke(null,info.getClazz().cast(payload), context);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
