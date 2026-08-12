package com.baisylia.modestmining.loot;

import com.baisylia.modestmining.item.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class CopperScrewLootModifier extends LootModifier {
    public static final MapCodec<CopperScrewLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance).and(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(m -> m.chance)
            ).apply(instance, CopperScrewLootModifier::new)
    );

    private final float chance;

    public CopperScrewLootModifier(LootItemCondition[] conditionsIn, float chance) {
        super(conditionsIn);
        this.chance = chance;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < chance) {
            generatedLoot.add(new ItemStack(ModItems.COPPER_SCREW.get()));
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
