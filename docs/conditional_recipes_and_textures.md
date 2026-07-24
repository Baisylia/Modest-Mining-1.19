# Adding Conditional Recipes & Texture

---

## Step 1: Add the Config Option

In `ModConfig.java`:

1. Declare the config variable near the top of the class
   ```java
   public static final ForgeConfigSpec.BooleanValue COPPER_FURNACE;
   ```

2. Add the config definition & register the condition inside `static { ... }`
   ```java
   // Under BUILDER.push("replacements");
   COPPER_FURNACE = BUILDER.comment("Furnace uses copper instead of cobblestone.").define("copper_furnace", false);

   // At the bottom of the static block, register the condition name for recipe JSONs:
   registerCondition("copper_furnace", COPPER_FURNACE);
   
   // Calling registerCondition() like this automatically enables both "copper_furnace" and "not_copper_furnace" for recipe JSONs
   ```

## Step 2: Create the Recipe JSON Files

To rework a recipe conditionally, create two JSON files:

### File 1: Default Recipe (used when a feature is toggled off)

Name: `furnace.json` (or your item's recipe name)

```json
{
  "conditions": [
    {
      "type": "modestmining:config_enabled",
      "feature": "not_copper_furnace"
    }
  ],
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "###",
    "# #",
    "###"
  ],
  "key": {
    "#": {
      "item": "minecraft:cobblestone"
    }
  },
  "result": {
    "item": "minecraft:furnace"
  }
}
```

### File 2: Reworked Recipe (used when a feature is toggled on)

Name: `furnace_copper.json`

```json
{
  "conditions": [
    {
      "type": "modestmining:config_enabled",
      "feature": "copper_furnace"
    }
  ],
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "###",
    "# #",
    "###"
  ],
  "key": {
    "#": {
      "item": "minecraft:copper_ingot"
    }
  },
  "result": {
    "item": "minecraft:furnace"
  }
}
```

---

## Step 3: (Optional) Conditional Texture Overrides

If your recipe rework also changes how the item/block looks, follow these steps:

### 1. Create a Resource Pack Folder

Create a folder inside `src/main/resources/resourcepacks/`:
e.g. `src/main/resources/resourcepacks/copper_furnace_textures/`

### 2. Add a `pack.mcmeta` inside that folder

```json
{
  "pack": {
    "pack_format": 9,
    "description": "Copper Furnace Texture Override"
  }
}
```

### 3. Add Replacement Textures

Place replacement PNG files at matching paths inside the resource pack folder:

e.g. `resourcepacks/copper_furnace_textures/assets/minecraft/textures/block/furnace_front.png`

### 4. Register the Pack in `ModestMining.java`

Add a line inside `addPackFinders` to register your feature resource pack:

```java
registerFeaturePack(event, "Modest Mining: Copper Furnace Override","copper_furnace_textures","copper_furnace");
```

That's it! When `copper_furnace = true`, mMining will automatically load the reworked recipe and swap in the replacement
textures.
