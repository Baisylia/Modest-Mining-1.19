package com.baisylia.modestmining.item.custom.tools;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;

public class ChiselItem extends DiggerItem {
    public ChiselItem(float attackDamage, float attackSpeed, Tier material, Properties properties) {
        super(material, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }
}
