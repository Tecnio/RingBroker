package io.ringbroker.core.sequence;

import lombok.Getter;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * Padded, CAS‑capable cursor to avoid false‑sharing between threads.
 */
public final class Sequence {
    static {
        try {
            VH = MethodHandles.lookup().findVarHandle(Sequence.class, "value", long.class);
        } catch (final ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @SuppressWarnings("unused")
    private long p1, p2, p3, p4, p5, p6, p7;
    @Getter
    private volatile long value;

    private static final VarHandle VH;
    @SuppressWarnings("unused")
    private long p8, p9, p10, p11, p12, p13, p14;

    public Sequence(final long initial) {
        VH.setRelease(this, initial);
    }

    public boolean cas(final long expect, final long update) {
        return VH.compareAndSet(this, expect, update);
    }
}
