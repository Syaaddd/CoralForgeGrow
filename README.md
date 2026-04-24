# CoralForgeGrow

A [Slimefun4](https://github.com/SlimefunGuguProject/Slimefun4) addon that adds a hardcore underwater gear progression system across 4 tiers — from basic Coral equipment up to legendary Void-tier armor, weapons, and utility items.

**Author:** Syaaddd  
**Version:** 1.0.0-SNAPSHOT  
**Minecraft:** Paper 1.21.1+  
**Dependencies:** Slimefun4, [OceanMinerGrow](https://github.com/Syaaddd/OceanMinerGrow)

---

## Machines

| Item | ID | Description |
|---|---|---|
| CoralForge Workbench | `CFG_WORKBENCH` | Main crafting station for all CoralForge items. Crafted with Prismarine Bricks + Furnace. |
| CoralForge Infuser | `CFG_INFUSER` | Legendary crafting station required for Void Alloy. Crafted in CoralForge Workbench using Obsidian + Crying Obsidian + Respawn Anchor. |

---

## Intermediate Materials

| Item | ID | Tier | Key Ingredients |
|---|---|---|---|
| Abyssal Ingot | `CFG_ABYSSAL_INGOT` | T2 | Abyssite x6 + Iron Ingot x2 |
| Tidal Alloy | `CFG_TIDAL_ALLOY` | T2 | Tridentite Shard x4 + Abyssal Ingot x2 |
| Pressure Plate Mat | `CFG_PRESSURE_PLATE_MAT` | T3 | Pressure Gem x3 + Abyssal Ingot x4 |
| Tidestone Slab | `CFG_TIDESTONE_SLAB` | T3 | Tidestone Fragment x8 + Pressure Gem x1 |
| Void Alloy | `CFG_VOID_ALLOY` | T4 | Void Crystal x2 + Pressure Plate Mat x2 + Abyssal Core x1 *(Infuser only)* |
| Core Shard | `CFG_CORE_SHARD` | T4 | Abyssal Core x2 + Tidestone Slab x1 |

---

## Armor

Armor effects are applied on a periodic ticker (configurable, default every 30 ticks / 1.5 s). Effects marked **water-only** activate when the player is submerged; effects marked **always** are active everywhere.

### T1 — Coral Set

| Piece | ID | Effects |
|---|---|---|
| Coral Helmet | `CFG_CORAL_HELMET` | Night Vision *(water)* |
| Coral Chestplate | `CFG_CORAL_CHESTPLATE` | Water Breathing *(water)* |
| Coral Leggings | `CFG_CORAL_LEGGINGS` | Speed I *(water)* |
| Coral Boots | `CFG_CORAL_BOOTS` | Depth Strider III *(enchant)* |

**Ingredients:** Coral Dust + Pearlstone

### T2 — Abyssal Set

| Piece | ID | Effects |
|---|---|---|
| Abyssal Helmet | `CFG_ABYSSAL_HELMET` | Night Vision + Conduit Power *(water)* |
| Abyssal Chestplate | `CFG_ABYSSAL_CHESTPLATE` | Water Breathing + Resistance I *(water)* |
| Abyssal Leggings | `CFG_ABYSSAL_LEGGINGS` | Speed II + Slow Falling *(water)* |
| Abyssal Boots | `CFG_ABYSSAL_BOOTS` | Depth Strider III *(enchant)* + Dolphin's Grace *(water)* |

**Ingredients:** Abyssite + Abyssal Ingot + Tridentite Shard

### T3 — Pressure Set

| Piece | ID | Effects |
|---|---|---|
| Pressure Helmet | `CFG_PRESSURE_HELMET` | Water Breathing *(water)* + Night Vision + Conduit Power III *(always)* |
| Pressure Chestplate | `CFG_PRESSURE_CHESTPLATE` | Water Breathing + Resistance II + Speed I *(water)* |
| Pressure Leggings | `CFG_PRESSURE_LEGGINGS` | Speed III *(water)* |
| Pressure Boots | `CFG_PRESSURE_BOOTS` | Depth Strider III + Feather Falling IV *(enchants)* + Slow Falling *(water)* |

**Ingredients:** Pressure Gem + Pressure Plate Mat + Tidestone Slab + Abyssite + Tridentite Shard

### T4 — Void Set (Legendary)

| Piece | ID | Effects |
|---|---|---|
| Void Helmet | `CFG_VOID_HELMET` | Water Breathing + Dolphin's Grace *(water)* + Night Vision + Conduit Power III + Haste II *(always)* |
| Void Chestplate | `CFG_VOID_CHESTPLATE` | Water Breathing + Speed II *(water)* + Resistance III *(always)* |
| Void Leggings | `CFG_VOID_LEGGINGS` | Speed IV *(water)* + Swift Sneak III *(enchant)* |
| Void Boots | `CFG_VOID_BOOTS` | Depth Strider III + Feather Falling IV *(enchants)* + Dolphin's Grace + Slow Falling *(water)* |

**Ingredients:** Void Alloy + Void Crystal + Abyssal Core + Pressure Gem

---

## Weapons & Tools

Swords apply bonus damage on hit. Most swords are **water-only** (bonus activates only when the attacker is in water). The Void Reaver applies bonus damage regardless of environment.

### T1 — Coral

| Item | ID | Bonus | Notes |
|---|---|---|---|
| Coral Dagger | `CFG_CORAL_DAGGER` | +7 damage | Water-only, no slowdown |
| Kelp Blade | `CFG_KELP_BLADE` | +8 damage | Water-only, applies Regen I on hit |
| Tide Pickaxe | `CFG_TIDE_PICKAXE` | — | Efficiency III + Aqua Affinity |

### T2 — Abyssal

| Item | ID | Bonus | Notes |
|---|---|---|---|
| Abyssal Blade | `CFG_ABYSSAL_BLADE` | +13 damage | Water-only, Smite III |
| Trident: Tide Rip | `CFG_TIDE_RIP_TRIDENT` | +11 damage | Riptide V + Loyalty III |
| Abyssal Drill | `CFG_ABYSSAL_DRILL` | — | Efficiency V + Fortune II |

### T3 — Pressure

| Item | ID | Bonus | Notes |
|---|---|---|---|
| Pressure Spear | `CFG_PRESSURE_SPEAR` | +18 damage | Riptide V + Impaling V + Channeling |
| Deep Cutter | `CFG_DEEP_CUTTER` | +20 damage | Water-only, Sharpness V, Regen II on hit |
| Abyss Excavator | `CFG_ABYSS_EXCAVATOR` | — | Efficiency VII + Silk Touch + Fortune III |

### T4 — Void (Legendary)

| Item | ID | Bonus | Notes |
|---|---|---|---|
| Void Reaver | `CFG_VOID_REAVER` | +28 damage | Always active, Sharpness V + Smite V, Regen II on hit |
| Void Trident | `CFG_VOID_TRIDENT` | +25 damage | Riptide V + Loyalty III + Impaling VII + Channeling |

---

## Utilities

| Item | ID | Function |
|---|---|---|
| Pearl Lantern | `CFG_PEARL_LANTERN` | Light level 15, does not extinguish underwater |
| Depth Gauge | `CFG_DEPTH_GAUGE` | Displays current Y zone and biome |
| Abyssal Torch Stack | `CFG_ABYSSAL_TORCH` | Torches that can be placed underwater |
| Pressure Compass | `CFG_PRESSURE_COMPASS` | Shows Y zone + nearest Ocean Monument direction |
| Tide Beacon | `CFG_TIDE_BEACON` | Underwater beacon with +50% range, grants Haste & Speed |
| Void Lens | `CFG_VOID_LENS` | Sonar with 5-block visual radius |

---

## Source Materials (from OceanMinerGrow)

All raw materials come from the **OceanMinerGrow** addon:

| Material | Slimefun ID | Role |
|---|---|---|
| Coral Dust | `OCEAN_MINER_CORAL_DUST` | T1 base material |
| Pearlstone | `OCEAN_MINER_PEARLSTONE` | T1 base material |
| Abyssite | `OCEAN_MINER_ABYSSITE` | T2 base material |
| Tridentite Shard | `OCEAN_MINER_TRIDENTITE_SHARD` | T2 weapon/armor material |
| Pressure Gem | `OCEAN_MINER_PRESSURE_GEM` | T3 material |
| Tidestone Fragment | `OCEAN_MINER_TIDESTONE_FRAG` | T3 material |
| Abyssal Core | `OCEAN_MINER_ABYSSAL_CORE` | T4 material |
| Void Crystal | `OCEAN_MINER_VOID_CRYSTAL` | T4 legendary material |

---

## Configuration (`config.yml`)

```yaml
coralforge:
  # Toggle entire item categories
  enable-coral-set: true
  enable-abyssal-set: true
  enable-pressure-set: true
  enable-void-set: true
  enable-weapons: true
  enable-utilities: true
  enable-machines: true

  # Armor effect tick interval (in ticks, 20 = 1 second)
  armor-effect-interval: 30
```

---

## Installation

1. Install [Slimefun4](https://github.com/SlimefunGuguProject/Slimefun4) and [OceanMinerGrow](https://github.com/Syaaddd/OceanMinerGrow).
2. Drop `CoralForgeGrow.jar` into your `plugins/` folder.
3. Restart the server.
4. Items appear under the **CoralForge** category in the Slimefun Guide.

## Building from Source

```bash
mvn clean package
```

Requires Java 21. The compiled jar is in `target/`.

---

## Bug Tracker

<https://github.com/Syaaddd/CoralForgeGrow/issues>