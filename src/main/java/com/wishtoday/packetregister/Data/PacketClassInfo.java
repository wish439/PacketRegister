package com.wishtoday.packetregister.Data;

import com.wishtoday.packetregister.Util.PacketState;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.lang.reflect.Method;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
public class PacketClassInfo<T extends CustomPayload> {
    private CustomPayload.Id<T> ID;
    private PacketCodec<PacketByteBuf, T> CODEC;
    private Method HANDLER;
    private PacketState state;
    private Class<? extends CustomPayload> clazz;
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PacketClassInfo<?> that = (PacketClassInfo<?>) o;
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public boolean hasEmpty() {
        return this.CODEC == null ||
                this.ID == null;
    }
}
