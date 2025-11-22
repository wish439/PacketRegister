package com.wishtoday.packetregister.Manager;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import net.minecraft.network.packet.CustomPayload;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings({"FieldMayBeFinal"})
public class PacketClassManager {
    @Getter
    private static PacketClassManager instance = new PacketClassManager();
    //key:a class path; ASM friendly: Example: "java/lang/String"
    private Map<String, PacketClassInfo<CustomPayload>> packetClassInfoMap;
    private PacketClassManager() {
        this.packetClassInfoMap = new Object2ObjectOpenHashMap<>();
    }
    public void putPacketClassInfo(
            String id
            , PacketClassInfo<CustomPayload> packetClassInfo) {
        id = processPath(id);
        if (packetClassInfoMap.containsKey(id)) throw new IllegalArgumentException(id + "class payload ID already exists!");
        packetClassInfoMap.put(id, packetClassInfo);
    }
    public PacketClassInfo<CustomPayload> getPacketClassInfo(
            String id) {
        id = processPath(id);
        return packetClassInfoMap.get(id);
    }
    public PacketClassInfo<CustomPayload> computeIfAbsent(
            String key
            , @NotNull Function<? super String
                    , ? extends PacketClassInfo<CustomPayload>> mappingFunction) {
        key = processPath(key);
        return packetClassInfoMap.computeIfAbsent(key, mappingFunction);
    }
    public Collection<PacketClassInfo<CustomPayload>> getAllPacketClassInfo() {
        return packetClassInfoMap.values();
    }
    /**
     * A check classpath format and auto process method
     * @param path The classpath
     * @return process's classpath
     */
    @NotNull
    private String processPath(@NotNull String path) {
        if (path.contains("."))
            return path.replace(".", "/");
        return path;
    }
}
