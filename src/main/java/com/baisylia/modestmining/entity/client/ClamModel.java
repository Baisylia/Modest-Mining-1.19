package com.baisylia.modestmining.entity.client;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.entity.custom.ClamEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClamModel extends AnimatedGeoModel<ClamEntity> {
    @Override
    public ResourceLocation getModelResource(ClamEntity object) {
        return new ResourceLocation(ModestMining.MOD_ID, "geo/clam.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ClamEntity object) {
        return new ResourceLocation(ModestMining.MOD_ID, "textures/entity/clam/clam.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ClamEntity animatable) {
        return new ResourceLocation(ModestMining.MOD_ID, "animations/clam.animation.json");
    }
}
