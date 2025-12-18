package net.mattias.capybara.core.entity.custom;

import net.mattias.capybara.core.block.ModBlocks;
import net.mattias.capybara.core.entity.ModEntities;
import net.mattias.capybara.core.entity.custom.goals.UntamedTemptGoal;
import net.mattias.capybara.core.gui.inventory.CapybaraInventoryMenu;
import net.mattias.capybara.core.item.ModItems;
import net.mattias.capybara.core.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.UUID;

public class CapybaraEntity extends TamableAnimal implements ContainerListener {

    private static final EntityDataAccessor<Integer> FUR_COLOR = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ARMOR_VARIANT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SHEARED = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SITTING = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_TOP_HAT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CHEF_HAT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_WITCH_HAT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CROWN = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PIRATE_HAT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PLUNGER_HEAD = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CHRISTMAS = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SANTA_HAT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ELF_HAT = SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int sitStillTicks = 0;
    private int regrowthTime = 0;

    private final SimpleContainer inventory = new SimpleContainer(2);

    public CapybaraEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
        this.setTame(false);
        this.inventory.addListener(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FUR_COLOR, 1);
        this.entityData.define(ARMOR_VARIANT, 0);
        this.entityData.define(DATA_SHEARED, false);
        this.entityData.define(DATA_SITTING, false);
        this.entityData.define(DATA_TOP_HAT, false);
        this.entityData.define(DATA_CHEF_HAT, false);
        this.entityData.define(DATA_WITCH_HAT, false);
        this.entityData.define(DATA_CROWN, false);
        this.entityData.define(DATA_PIRATE_HAT, false);
        this.entityData.define(DATA_PLUNGER_HEAD, false);
        this.entityData.define(DATA_CHRISTMAS, false);
        this.entityData.define(DATA_SANTA_HAT, false);
        this.entityData.define(DATA_ELF_HAT, false);

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.ARMOR, 0.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.1));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new UntamedTemptGoal(this, 1.2));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.isSitting()) {
            this.sitStillTicks++;
            this.getNavigation().stop();
            this.setTarget(null);
            this.setNoActionTime(Integer.MAX_VALUE);

            if (!this.onGround() && !this.isInWater() && !this.isPassenger()) {
                this.setDeltaMovement(this.getDeltaMovement());
            } else {
                this.setDeltaMovement(0, 0, 0);
            }
        } else {
            this.sitStillTicks = 0;
        }

        if (!this.level().isClientSide() && this.isSheared()) {
            this.regrowthTime = Math.max(0, this.regrowthTime - 1);
            if (this.regrowthTime == 0) {
                this.setSheared(false);
            }
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            this.idleAnimationTimeout--;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTick * 6F, 1f) : 0f;
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame()
                    || (itemstack.is(Items.SUGAR_CANE) && !this.isTame())
                    || itemstack.is(Items.SEAGRASS)
                    || itemstack.is(Items.SHEARS);
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        if (itemstack.is(Items.SHEARS)
                && !this.isSheared()
                && !this.isBaby()) {

            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            int dropCount = 2 + this.random.nextInt(2);

            ItemStack furDrop = this.isChristmasCapybara()
                    ? new ItemStack(ModBlocks.WHITE_CAPYBARA_FUR_BLOCK.get())
                    : this.getFurDropForColor(this.getFurColor());

            for (int i = 0; i < dropCount; i++) {
                this.spawnAtLocation(furDrop.copy());
            }

            itemstack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            this.setSheared(true);
            this.regrowthTime = 6000;
            return InteractionResult.SUCCESS;
        }

        if (itemstack.is(Items.SUGAR_CANE)) {
            if (this.isTame()) {
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(2.0F);
                    if (!player.getAbilities().instabuild) itemstack.shrink(1);
                    this.level().playSound(null, this, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            } else {
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (this.isTame() && this.isOwnedBy(player)) {
            if (itemstack.is(Items.SEAGRASS) && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            if (player.isShiftKeyDown()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(
                            serverPlayer,
                            new SimpleMenuProvider(
                                    (windowId, playerInventory, playerEntity) ->
                                            new CapybaraInventoryMenu(windowId, playerInventory, this),
                                    this.getDisplayName()
                            ),
                            buf -> buf.writeVarInt(this.getId())
                    );
                }
                return InteractionResult.SUCCESS;
            }

            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }


    @Override
    public void containerChanged(Container container) {
        if (container != this.inventory) return;

        ItemStack armor = this.inventory.getItem(1);
        if (armor.is(ModItems.CAPYBARA_IRON_ARMOR.get())) {
            this.setArmorVariant(1);
            this.getAttribute(Attributes.ARMOR).setBaseValue(3.0D);
        } else if (armor.is(ModItems.CAPYBARA_GOLD_ARMOR.get())) {
            this.setArmorVariant(2);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2.0D);
        } else if (armor.is(ModItems.CAPYBARA_DIAMOND_ARMOR.get())) {
            this.setArmorVariant(3);
            this.getAttribute(Attributes.ARMOR).setBaseValue(5.0D);
        } else {
            this.setArmorVariant(0);
            this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
        }

        ItemStack cosmetic = this.inventory.getItem(0);
        this.setTopHat(cosmetic.is(ModItems.CAPYBARA_TOP_HAT.get()));
        this.setChefHat(cosmetic.is(ModItems.CAPYBARA_CHEF_HAT.get()));
        this.setWitchHat(cosmetic.is(ModItems.CAPYBARA_WITCH_HAT.get()));
        this.setCrown(cosmetic.is(ModItems.CAPYBARA_CROWN.get()));
        this.setPirateHat(cosmetic.is(ModItems.CAPYBARA_PIRATE_HAT.get()));
        this.setPlungerHead(cosmetic.is(ModItems.CAPYBARA_PLUNGER_HEAD.get()));
        this.setSantaHat(cosmetic.is(ModItems.CAPYBARA_SANTA_HAT.get()));
        this.setElfHat(cosmetic.is(ModItems.CAPYBARA_ELF_HAT.get()));

    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (!this.isChristmasCapybara()) {
            tag.putInt("FurColor", this.getFurColor());
        }
        tag.putBoolean("Sheared", this.isSheared());
        if (this.isSheared()) {
            tag.putInt("RegrowthTime", this.regrowthTime);
        }
        tag.putInt("ArmorVariant", this.getArmorVariant());
        tag.putBoolean("isSitting", this.isSitting());
        tag.putBoolean("ChristmasCapybara", this.isChristmasCapybara());

        tag.putBoolean("TopHat", this.hasTopHat());
        tag.putBoolean("ChefHat", this.hasChefHat());
        tag.putBoolean("WitchHat", this.hasWitchHat());
        tag.putBoolean("Crown", this.hasCrown());
        tag.putBoolean("PirateHat", this.hasPirateHat());
        tag.putBoolean("PlungerHead", this.hasPlungerHead());
        tag.putBoolean("SantaHat", this.hasSantaHat());
        tag.putBoolean("ElfHat", this.hasElfHat());

        CompoundTag equipTag = new CompoundTag();
        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                equipTag.put("Slot" + i, stack.save(new CompoundTag()));
            }
        }
        tag.put("CapybaraInventory", equipTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("ChristmasCapybara")) {
            this.setChristmasCapybara(tag.getBoolean("ChristmasCapybara"));
            if (this.isChristmasCapybara()) {
                this.setFurColor(5);
            }
        }

        if (!this.isChristmasCapybara() && tag.contains("FurColor")) {
            this.setFurColor(tag.getInt("FurColor"));
        }

        if (tag.contains("Sheared")) {
            this.setSheared(tag.getBoolean("Sheared"));
            if (this.isSheared() && tag.contains("RegrowthTime")) {
                this.regrowthTime = tag.getInt("RegrowthTime");
            }
        }
        if (this.isBaby() && this.isSheared()) {
            this.setSheared(false);
            this.regrowthTime = 0;
        }
        if (tag.contains("ArmorVariant")) this.setArmorVariant(tag.getInt("ArmorVariant"));
        this.setSitting(tag.getBoolean("isSitting"));

        if (tag.contains("TopHat")) this.setTopHat(tag.getBoolean("TopHat"));
        if (tag.contains("ChefHat")) this.setChefHat(tag.getBoolean("ChefHat"));
        if (tag.contains("WitchHat")) this.setWitchHat(tag.getBoolean("WitchHat"));
        if (tag.contains("Crown")) this.setCrown(tag.getBoolean("Crown"));
        if (tag.contains("PirateHat")) this.setPirateHat(tag.getBoolean("PirateHat"));
        if (tag.contains("PlungerHead")) this.setPlungerHead(tag.getBoolean("PlungerHead"));
        if (tag.contains("SantaHat")) this.setSantaHat(tag.getBoolean("SantaHat"));
        if (tag.contains("ElfHat")) this.setElfHat(tag.getBoolean("ElfHat"));

        if (tag.contains("CapybaraInventory")) {
            CompoundTag equipTag = tag.getCompound("CapybaraInventory");
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                if (equipTag.contains("Slot" + i)) {
                    this.inventory.setItem(i, ItemStack.of(equipTag.getCompound("Slot" + i)));
                } else {
                    this.inventory.setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }

        if (!this.isSheared()) {
            int furDropCount = 1 + this.random.nextInt(2) + looting;

            for (int i = 0; i < furDropCount; i++) {
                ItemStack furDrop = this.isChristmasCapybara()
                        ? new ItemStack(ModBlocks.WHITE_CAPYBARA_FUR_BLOCK.get())
                        : this.getFurDropForColor(this.getFurColor());

                if (!furDrop.isEmpty()) {
                    this.spawnAtLocation(furDrop);
                }
            }
        }

        int muttonDropCount = 1 + this.random.nextInt(2);
        for (int i = 0; i < muttonDropCount; i++) {
            this.spawnAtLocation(new ItemStack(Items.MUTTON));
        }
    }

    private ItemStack getFurDropForColor(int furColor) {
        return switch (furColor) {
            case 1 -> new ItemStack(ModBlocks.CAPYBARA_FUR_BLOCK.get());
            case 2 -> new ItemStack(ModBlocks.LIGHT_GRAY_CAPYBARA_FUR_BLOCK.get());
            case 3 -> new ItemStack(ModBlocks.GRAY_CAPYBARA_FUR_BLOCK.get());
            case 4 -> new ItemStack(ModBlocks.CREAM_CAPYBARA_FUR_BLOCK.get());
            case 5 -> new ItemStack(ModBlocks.WHITE_CAPYBARA_FUR_BLOCK.get());
            case 6 -> new ItemStack(ModBlocks.RED_CAPYBARA_FUR_BLOCK.get());
            case 7 -> new ItemStack(ModBlocks.DARK_BROWN_CAPYBARA_FUR_BLOCK.get());
            case 8 -> new ItemStack(ModBlocks.LIGHT_BROWN_CAPYBARA_FUR_BLOCK.get());
            case 9 -> new ItemStack(ModBlocks.GOLD_CAPYBARA_FUR_BLOCK.get());
            case 10 -> new ItemStack(ModBlocks.BLACK_CAPYBARA_FUR_BLOCK.get());
            default -> new ItemStack(ModBlocks.CAPYBARA_FUR_BLOCK.get());
        };
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel lvl, AgeableMob parent) {
        CapybaraEntity baby = ModEntities.CAPYBARA.get().create(lvl);
        if (baby != null) {
            CapybaraEntity other = (CapybaraEntity) parent;

            boolean parentIsChristmas = this.isChristmasCapybara();
            boolean otherParentIsChristmas = other.isChristmasCapybara();

            if (parentIsChristmas && otherParentIsChristmas) {
                baby.setChristmasCapybara(true);
                baby.setFurColor(5);
            } else if (parentIsChristmas || otherParentIsChristmas) {
                baby.setChristmasCapybara(this.random.nextFloat() < 0.5F);
                if (baby.isChristmasCapybara()) {
                    baby.setFurColor(5);
                }
            } else {
                baby.setChristmasCapybara(false);
                baby.setFurColor(this.random.nextBoolean() ? this.getFurColor() : other.getFurColor());
            }

            baby.setArmorVariant(0);
            baby.setSheared(false);
            baby.setTopHat(false);
            baby.setChefHat(false);
            baby.setWitchHat(false);
            baby.setCrown(false);
            baby.setPirateHat(false);
            baby.setPlungerHead(false);
            baby.setSantaHat(false);
            baby.setElfHat(false);

            UUID owner = this.getOwnerUUID();
            if (owner != null) {
                baby.setOwnerUUID(owner);
                baby.setTame(true);
            }
        }
        return baby;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor lvl, DifficultyInstance diff, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        spawnData = super.finalizeSpawn(lvl, diff, reason, spawnData, tag);
        int furColorMax = 10;

        if (tag != null && tag.contains("Sheared")) {
            this.setSheared(tag.getBoolean("Sheared"));
            if (this.isSheared() && tag.contains("RegrowthTime")) {
                this.regrowthTime = tag.getInt("RegrowthTime");
            }
        }

        if (tag != null && tag.contains("ChristmasCapybara")) {
            this.setChristmasCapybara(tag.getBoolean("ChristmasCapybara"));
        } else if (reason == MobSpawnType.NATURAL || reason == MobSpawnType.SPAWNER || reason == MobSpawnType.SPAWN_EGG) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
                if (this.random.nextFloat() < 0.25F) {
                    this.setChristmasCapybara(true);
                }
            }
        }

        if (!this.isChristmasCapybara()) {
            if (tag != null && tag.contains("FurColor")) {
                this.setFurColor(tag.getInt("FurColor"));
            } else {
                this.setFurColor(this.random.nextInt(furColorMax) + 1);
            }
        } else {
            this.setFurColor(5);
        }

        if (tag != null) {
            this.setTopHat(tag.getBoolean("TopHat"));
            this.setChefHat(tag.getBoolean("ChefHat"));
            this.setWitchHat(tag.getBoolean("WitchHat"));
            this.setCrown(tag.getBoolean("Crown"));
            this.setPirateHat(tag.getBoolean("PirateHat"));
            this.setPlungerHead(tag.getBoolean("PlungerHead"));
            this.setSantaHat(tag.getBoolean("SantaHat"));
            this.setElfHat(tag.getBoolean("ElfHat"));

        }

        if (this.isBaby()) {
            this.setAge(-48000);
        }

        return spawnData;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) return false;
        Entity entity = source.getEntity();
        if (!this.level().isClientSide) {
            this.setOrderedToSit(false);
        }
        if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
            amount = (amount + 1.0F) / 2.0F;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SEAGRASS);
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextFloat() < 0.5f ? ModSounds.CAPYBARA_AMBIENT.get() : ModSounds.CAPYBARA_AMBIENT_2.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.CAPYBARA_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CAPYBARA_DEATH.get();
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public int getFurColor() { return this.entityData.get(FUR_COLOR); }
    public void setFurColor(int color) { this.entityData.set(FUR_COLOR, color); }

    public int getArmorVariant() { return this.entityData.get(ARMOR_VARIANT); }
    public void setArmorVariant(int variant) { this.entityData.set(ARMOR_VARIANT, variant); }

    public boolean isSheared() { return this.entityData.get(DATA_SHEARED); }
    public void setSheared(boolean sheared) { this.entityData.set(DATA_SHEARED, sheared); }

    public boolean isSitting() { return this.entityData.get(DATA_SITTING); }
    public void setSitting(boolean sitting) { this.entityData.set(DATA_SITTING, sitting); }

    public boolean hasTopHat() { return this.entityData.get(DATA_TOP_HAT); }
    public void setTopHat(boolean val) { this.entityData.set(DATA_TOP_HAT, val); }

    public boolean hasChefHat() { return this.entityData.get(DATA_CHEF_HAT); }
    public void setChefHat(boolean val) { this.entityData.set(DATA_CHEF_HAT, val); }

    public boolean hasWitchHat() { return this.entityData.get(DATA_WITCH_HAT); }
    public void setWitchHat(boolean val) { this.entityData.set(DATA_WITCH_HAT, val); }

    public boolean hasCrown() { return this.entityData.get(DATA_CROWN); }
    public void setCrown(boolean val) { this.entityData.set(DATA_CROWN, val); }

    public boolean hasPirateHat() { return this.entityData.get(DATA_PIRATE_HAT); }
    public void setPirateHat(boolean val) { this.entityData.set(DATA_PIRATE_HAT, val); }

    public boolean hasPlungerHead() { return this.entityData.get(DATA_PLUNGER_HEAD); }
    public void setPlungerHead(boolean val) { this.entityData.set(DATA_PLUNGER_HEAD, val); }

    public boolean isChristmasCapybara() { return this.entityData.get(DATA_CHRISTMAS); }
    public void setChristmasCapybara(boolean val) { this.entityData.set(DATA_CHRISTMAS, val); }

    public boolean hasSantaHat() { return this.entityData.get(DATA_SANTA_HAT); }
    public void setSantaHat(boolean val) { this.entityData.set(DATA_SANTA_HAT, val); }

    public boolean hasElfHat() { return this.entityData.get(DATA_ELF_HAT); }
    public void setElfHat(boolean val) { this.entityData.set(DATA_ELF_HAT, val); }

    @Override
    public boolean isOrderedToSit() { return this.isSitting(); }

    @Override
    public void setOrderedToSit(boolean sitting) { this.setSitting(sitting); }
}