package com.wishtoday.packetregister.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class RegisterInfo<T extends CustomPayload> {
    private CustomPayload.Id<T> ID;
    private PacketCodec<PacketByteBuf, T> CODEC;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegisterInfo<?> that)) return false;
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }

    public boolean hasEmpty() {
        return this.ID == null ||
                this.CODEC == null;
    }
}
