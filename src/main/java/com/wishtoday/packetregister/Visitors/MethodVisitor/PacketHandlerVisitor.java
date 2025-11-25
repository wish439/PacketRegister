package com.wishtoday.packetregister.Visitors.MethodVisitor;

import com.wishtoday.packetregister.Data.PacketClassInfo;
import com.wishtoday.packetregister.Manager.PacketClassManager;
import com.wishtoday.packetregister.Util.ClassUtil;
import com.wishtoday.packetregister.Util.MethodGetter;
import com.wishtoday.packetregister.Util.PacketState;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Method;

public class PacketHandlerVisitor extends MethodVisitor {
    private final String classPath;
    private final String methodName;
    private final PacketState state;
    private Method method;

    public PacketHandlerVisitor(String classPath
            , String methodName
            , PacketState state) {
        super(Opcodes.ASM9);
        this.classPath = classPath;
        this.methodName = methodName;
        this.state = state;
    }

    @Override
    public AnnotationVisitor visitAnnotation(
            String descriptor
            , boolean visible) {
        if (!descriptor.equals("Lcom/wishtoday/Annotation/Handler;")) return super.visitAnnotation(descriptor, visible);
        tryGetMethod();
        return super.visitAnnotation(descriptor, visible);
    }

    private void tryGetMethod() {
        Class<?> aClass = ClassUtil.getClass(this.classPath);
        if (aClass == null) return;
        if (state.getEnvType() != FabricLoader.getInstance().getEnvironmentType()) return;
        Method get = new MethodGetter(this.classPath, this.methodName)
                .loadAndGetWithArgs(ClassUtil.getClass(this.classPath)
                        , ClassUtil.getClass(
                                this.state.getContextClass()
                        )
                );
        if (get == null) return;
        this.method = get;
    }

    @Override
    public void visitEnd() {
        if (this.method == null) return;
        PacketClassManager.getInstance()
                .computeIfAbsent(this.classPath, e -> new PacketClassInfo<>())
                .setHANDLER(this.method);
    }
}
