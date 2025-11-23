package com.wishtoday.packetregister.Util;

import lombok.extern.log4j.Log4j2;
import net.minecraft.network.packet.CustomPayload;

@Log4j2
public class CustomPayloadUtils {
    public static CustomPayload.Id<CustomPayload> createCustomID(String path) {
        return new CustomPayload.Id<>(IdentifierCreator.create(path));
    }
}
