package com.wishtoday.packetregister.Register;

import com.wishtoday.Annotation.Initialize;
import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Util.PacketState;
import com.wishtoday.packetregister.client.ClientReceiverRegister;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
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
            if (classInfo.registerInfoHasEmpty()) {
                log.warn("Packet ID{}has null or empty", classInfo.getID());
                continue;
            }
            log.info("Packet ID{} will reg", classInfo.getID());
            chooseRegister(classInfo);
        }
    }
    private static void chooseRegister(PacketClassInfo<CustomPayload> info) {
        PacketState state = info.getState();
        switch (state) {
            case S2C -> S2CRegister(info);
            case C2S -> C2SRegister(info);
        }
        registerReceiver(info);
    }
    private static void S2CRegister(PacketClassInfo<CustomPayload> info) {
        PayloadTypeRegistry.playS2C().register(info.getID(), info.getCODEC());
        log.info("S2C Register Success");
    }
    private static void C2SRegister(PacketClassInfo<CustomPayload> info) {
        PayloadTypeRegistry.playC2S().register(info.getID(), info.getCODEC());
        log.info("C2S Register Success");
    }
    private static void registerReceiver(PacketClassInfo<CustomPayload> info) {
        EnvType type = FabricLoader.getInstance().getEnvironmentType();
        ReceiverRegister register;
        switch (type) {
            case CLIENT -> register = new ClientReceiverRegister();
            case SERVER -> register = new ServerReceiverRegister();
            default -> register = null;
        }
        if (register == null) return;
        if (register.getEnvType() != info.getState().getEnvType()) return;
        register.register(info);
    }
}
