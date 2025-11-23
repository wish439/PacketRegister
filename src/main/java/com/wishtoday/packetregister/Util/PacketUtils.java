package com.wishtoday.packetregister.Util;

import net.minecraft.network.packet.CustomPayload;

public class PacketUtils {

    public static CustomPayload.Id<? extends CustomPayload> createID(String path) {
        return new CustomPayload.Id<>(IdentifierCreator.create(path));
    }

    /*public static PacketCodec<PacketByteBuf, ? extends CustomPayload> createEmptyCodec() {

    }*/
}
