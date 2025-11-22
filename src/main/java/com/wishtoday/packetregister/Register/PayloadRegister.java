package com.wishtoday.packetregister.Register;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Util.PacketState;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.packet.CustomPayload;

public class PayloadRegister {
    public static void register() {
        PacketClassManager.getInstance()
                .getAllPacketClassInfo()
                .forEach(PayloadRegister::chooseRegister);
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
    }
    private static void C2SRegister(PacketClassInfo<CustomPayload> info) {
        PayloadTypeRegistry.playC2S().register(info.getID(), info.getCODEC());
    }
}
