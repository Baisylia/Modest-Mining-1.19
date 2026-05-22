package com.baisylia.modestmining.recipe;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum MillingBookCategory implements StringRepresentable {

    ORES("ores"),
    PLANTS("plants"),
    BLOCKS("blocks"),
    TOOLS("tools"),
    MISC("misc");

    public static final EnumCodec<MillingBookCategory> CODEC = StringRepresentable.fromEnum(MillingBookCategory::values);

    private final String name;

    MillingBookCategory(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

}