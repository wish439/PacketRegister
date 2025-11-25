package com.wishtoday.packetregister.Data;

import com.wishtoday.packetregister.Util.PacketState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.lang.reflect.Method;

@NoArgsConstructor
public class PacketClassInfo<T extends CustomPayload> {
    private RegisterInfo<T> registerInfo;
    @Getter
    @Setter
    private Method HANDLER;
    @Getter
    @Setter
    private PacketState state;
    @Getter
    @Setter
    private Class<? extends CustomPayload> clazz;

    public void setID(CustomPayload.Id<T> id) {
        checkRegisterInfo();
        this.registerInfo.setID(id);
    }

    public void setCODEC(PacketCodec<PacketByteBuf, T> codec) {
        checkRegisterInfo();
        this.registerInfo.setCODEC(codec);
    }

    public CustomPayload.Id<T> getID() {
        checkRegisterInfo();
        return this.registerInfo.getID();
    }

    public PacketCodec<PacketByteBuf, T> getCODEC() {
        checkRegisterInfo();
        return this.registerInfo.getCODEC();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PacketClassInfo<?> that)) return false;
        return this.registerInfo.equals(that.registerInfo);
    }

    @Override
    public int hashCode() {
        return this.registerInfo.hashCode();
    }

    public boolean hasEmpty() {
        return this.registerInfo.getCODEC() == null ||
                this.registerInfo.getID() == null ||
                this.clazz == null ||
                this.HANDLER == null ||
                this.state == null;
    }

    public boolean registerInfoHasEmpty() {
        return this.registerInfo.hasEmpty();
    }
    private void checkRegisterInfo() {
        if (this.registerInfo == null) this.registerInfo = new RegisterInfo<>();
    }
}
