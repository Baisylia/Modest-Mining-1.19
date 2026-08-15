# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.3.6] - 2026-08-15

### Fixed

- Fix creative tab crash.

## [1.3.5] - 2026-08-14

### Fixed

- Reworked Reliable Remover integration.

## [1.3.4] - 2026-08-14

### Changed

- Reworked steel/bronze equipment recipes.

## [1.3.3] - 2026-08-14

### Fixed

- Reworked enchantment logic to be more mod compatible.

## [1.3.2] - 2026-08-14

### Fixed

- Fixed spyglass recipe condition.

## [1.3.1] - 2026-08-14

### Added

- Added new sounds for stone/wooden javelins.
- Added new sounds for critical javelin hits.

### Changed

- Prismarite javelins are no longer slowed in water.
- Cleaned up tag definitions.

### Fixed

- Fixed steel/bronze javelin textures.
- Fixed issues with javelin enchantments.
- Fixed javelin subtitles saying trident.
- Fixed not being able to start sprinting while holding a javelin.

## [1.3.0] - 2026-08-14

### Added

- Drowned can now spawn holding javelins and will throw them at targets (configurable).
- Zombies (and their variants) can now spawn holding javelins and will throw them at targets, same as drowned
  (configurable).
- Zombies can now throw tridents they're holding, same as javelins (configurable).
- Added a "Weapons" config category:
    - Toggle drowned/zombies spawning with javelins.
    - Toggle zombies throwing javelins.
    - Toggle zombies throwing tridents.
    - Toggle removal of the movement slowdown for throwing javelins.
    - Enable the same slowdown removal and critical damage bonus as javelins for tridents.
    - Added a javelin ranged damage multiplier option to tune thrown javelin damage.
- Prismarite and Valkyrium javelins now support Riptide and Channeling respectively.
- Added a tiered fuel system for the Forge: fuels are now grouped into tiers (tier 1/tier 2) that gate which forging
  recipes they can power, with matching EMI/JEI recipe displays.

### Changed

- Reworked javelin damage: thrown javelins now deal an additional 1.5x damage when thrown while sprinting.
- Reworked thrown javelin damage to scale off the javelin's base attack damage.
- Tweaked thrown javelin rendering.
- Coke, Coke Block, Coke Chunk and Blaze Powder are now all valid tier 1 Forge fuels.
- Adjusted javelin handheld/throwing models.
- Reworked the Forge's recipes to fit the new fuel tier system.
- Many vanilla and modded recipes (tools, armor, and several modded items) that previously required a specific hardcoded
  fuel item now require a Forge fuel tier tag instead, so any tier-appropriate fuel can be used.
- Renamed "rosegold" to "rose gold".
- Reworked Reliable Remover integration to be more generally compatible.
- Adjusted creative tab positions.

### Fixed

- Fixed items not being removed from Reliable Remover when config options were disabled.
- Fixed steel/bronze forging recipes when replacement options were enabled.
- Fixed issues with Clam loot.
- Fixed Clam model breaking particles not acting properly.

### Removed

- Removed fuel tier requirement from most vanilla equipment forging.
- Removed javelin slowdown (configurable).

## [1.2.1] - 2026-08-12

### Changed

- Tweaked Meteorite structure.

## [1.2.0] - 2026-08-12

### Added

- Added Meteorites!
    - Meteorites rarely generate in the overworld and can be mined for Meteoric Scrap, which is now used to craft
      Valkyrium.
- Added additional config options for Copper Screw loot.

### Changed

- Reworked Copper Screw archeology loot injection.

## [1.1.4] - 2026-08-12

### Fixed

- Fixed attribute tooltips.

## [1.1.3] - 2026-08-12

### Added

- Added attribute tooltips.

### Changed

- Rebalanced Valkyrium recipes.

## [1.1.2] - 2026-08-12

### Fixed

- Fixed log spam.

## [1.1.1] - 2026-08-12

### Added

- Added Manual Labour support for hammers.
- Added missing hammers to the `c:hammers` tag.
- Added Valkyrium recipe.

### Fixed

- Fixed issues with Forging fuels.
- Cleaned up redundant tags.
- Improved EMI recipe autofill.
- Fixed lit Aluminium Forge textures.
- Fixed Smoker and Blast Furnace textures with the optional resource pack enabled.

## [1.1.0] - 2026-08-12

### Added

- Added copper screws to archeology loot.
- Added a config option to disable copper screws in loot.

### Fixed

- Fixed millstone texture.
- Fixed recipe duplication bugs.
- Fixed bronze and steel sword recipes.
- Fixed bronze and steel armor being stackable.
- Fixed clam model.
- Fixed clam duplication.
- Fixed issues with the JEI plugin.

### Removed

- Removed recipe book category (wasn't working properly anyway).
- Removed accidental dust smelting recipes (dust can only be blasted).

## [1.0.0] - 2026-08-11

- Initial release.