### Added

- Drowned can now spawn holding javelins and will throw them at targets (configurable).
- Zombies (and their variants) can now spawn holding javelins and will throw them at targets, same as drowned (configurable).
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

### Fixed

- Fixed items not being removed from Reliable Remover when config options were disabled.
- Fixed steel/bronze forging recipes when replacement options were enabled.
- Fixed issues with Clam loot.
- Fixed Clam model breaking particles not acting properly.

### Removed

- Removed fuel tier requirement from most vanilla equipment forging.
- Removed javelin slowdown (configurable).