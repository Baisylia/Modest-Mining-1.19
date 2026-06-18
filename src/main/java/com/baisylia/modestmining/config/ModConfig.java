package com.baisylia.modestmining.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue FLINT_REPLACES_WOOD;
    public static final ForgeConfigSpec.BooleanValue BRONZE_REPLACES_STONE;
    public static final ForgeConfigSpec.BooleanValue STEEL_REPLACES_IRON;
    public static final ForgeConfigSpec.BooleanValue FORGE_USES_ALUMINIUM;

    static {
        BUILDER.comment("Modest Mining Configuration");
        BUILDER.push("replacements");

        FLINT_REPLACES_WOOD = BUILDER
                .comment(
                        "When enabled, vanilla wooden tools will be removed from the game (replaced by flint tools).",
                        "Requires Item Obliterator to be installed."
                )
                .define("flint_replaces_wood", false);

        BRONZE_REPLACES_STONE = BUILDER
                .comment(
                        "When enabled, vanilla stone tools will be removed from the game (replaced by bronze tools).",
                        "Requires Item Obliterator to be installed."
                )
                .define("bronze_replaces_stone", false);

        STEEL_REPLACES_IRON = BUILDER
                .comment(
                        "When enabled, vanilla iron tools and armour will be removed from the game (replaced by steel tools/armour).",
                        "Requires Item Obliterator to be installed."
                )
                .define("steel_replaces_iron", false);

        FORGE_USES_ALUMINIUM = BUILDER
                .comment(
                        "When enabled, the Forge block is crafted using aluminium ingots instead of iron ingots.",
                        "The original iron recipe will be disabled."
                )
                .define("forge_uses_aluminium", false);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
