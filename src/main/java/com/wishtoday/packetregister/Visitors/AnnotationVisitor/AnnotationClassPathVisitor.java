package com.wishtoday.packetregister.Visitors.AnnotationVisitor;

import lombok.Getter;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

@Getter
public abstract class AnnotationClassPathVisitor extends AnnotationVisitor {
    protected String classPath;
    public AnnotationClassPathVisitor(String classPath) {
        super(Opcodes.ASM9);
        this.classPath = classPath;
    }
}
