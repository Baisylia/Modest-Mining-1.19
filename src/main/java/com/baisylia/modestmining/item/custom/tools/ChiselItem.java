package com.baisylia.modestmining.item.custom.tools;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;

public class ChiselItem extends DiggerItem {
    public ChiselItem(float v, float v1, Tier tier, Properties properties) {
        super(v, v1, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }
}
