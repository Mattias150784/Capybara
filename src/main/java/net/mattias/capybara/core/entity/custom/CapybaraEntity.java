package net.mattias.capybara.core.entity.custom;

import net.mattias.capybara.core.block.ModBlocks;
import net.mattias.capybara.core.entity.ModEntities;
import net.mattias.capybara.core.entity.custom.goals.UntamedTemptGoal;
import net.mattias.capybara.core.gui.inventory.CapybaraInventoryMenu;
import net.mattias.capybara.core.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CapybaraEntity extends TamableAnimal implements ContainerListener {

    private static final EntityDataAccessor<Integer> FUR_COLOR =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SNOUT_VARIANT =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EARS_VARIANT =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LEGS_VARIANT =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<Integer> PATTERN_VARIANT =
//            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ARMOR_VARIANT =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SITTING =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_TOP_HAT =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SUPERMAN_CAPE =
            SynchedEntityData.defineId(CapybaraEntity.class, EntityDataSerializers.BOOLEAN);

    private final SimpleContainer capybaraInventory = new SimpleContainer(3);

    public CapybaraEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
        this.setTame(false);
        this.capybaraInventory.addListener(this);
    }

    public boolean hasTopHatEquipped() {
        ItemStack cosmeticSlot = capybaraInventory.getItem(0);
        return cosmeticSlot.is(ModItems.CAPYBARA_TOP_HAT.get());
    }

//    public boolean hasSupermanCapeEquipped() {
//        ItemStack cosmeticSlot = capybaraInventory.getItem(0);
//        return cosmeticSlot.is(ModItems.CAPYBARA_SUPERMAN_CAPE.get());
//    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int sitStillTicks = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (this.isSitting()) {
            this.sitStillTicks++;
            this.getNavigation().stop();
            this.setTarget(null);
            this.setNoActionTime(Integer.MAX_VALUE);

            if (!this.onGround() && !this.isInWater() && !this.isPassenger()) {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z);
            } else {
                this.setDeltaMovement(0, 0, 0);
            }
        } else {
            this.sitStillTicks = 0;
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(FUR_COLOR, 1);
        entityData.define(SNOUT_VARIANT, 1);
        entityData.define(EARS_VARIANT, 1);
        entityData.define(LEGS_VARIANT, 1);
//        entityData.define(PATTERN_VARIANT, -1);
        entityData.define(ARMOR_VARIANT, 0);
        entityData.define(DATA_SITTING, false);
        entityData.define(DATA_TOP_HAT, false);
        entityData.define(DATA_SUPERMAN_CAPE, false);

    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new BreedGoal(this, 1.1));
        goalSelector.addGoal(2, new UntamedTemptGoal(this, 1.2));
        goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.ARMOR, 0.0);
    }

    public SimpleContainer getInventory() {
        return capybaraInventory;
    }

    @Override
    public void containerChanged(Container container) {
        if (container == capybaraInventory) {
            ItemStack armor = capybaraInventory.getItem(1);
            if (armor.is(ModItems.CAPYBARA_IRON_ARMOR.get())) {
                setArmorVariant(1);
                if (this.getAttribute(Attributes.ARMOR) != null) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(3.0D);
                }
            } else if (armor.is(ModItems.CAPYBARA_GOLD_ARMOR.get())) {
                setArmorVariant(2);
                if (this.getAttribute(Attributes.ARMOR) != null) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(2.0D);
                }
            } else if (armor.is(ModItems.CAPYBARA_DIAMOND_ARMOR.get())) {
                setArmorVariant(3);
                if (this.getAttribute(Attributes.ARMOR) != null) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(5.0D);
                }
            } else {
                setArmorVariant(0);
                if (this.getAttribute(Attributes.ARMOR) != null) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
                }
            }

            ItemStack cosmetic = capybaraInventory.getItem(0);
            if (cosmetic.is(ModItems.CAPYBARA_TOP_HAT.get())) {
                setTopHat(true);
            } else {
                setTopHat(false);
            }
//            if (cosmetic.is(ModItems.CAPYBARA_SUPERMAN_CAPE.get())) {
//                setSupermanCape(true);
//            }
//            else {
//                setSupermanCape(false);
//            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel lvl, AgeableMob parent) {
        CapybaraEntity baby = ModEntities.CAPYBARA.get().create(lvl);
        if (baby != null) {
            CapybaraEntity other = (CapybaraEntity) parent;
            baby.setFurColor(this.random.nextBoolean() ? this.getFurColor() : other.getFurColor());
            baby.setSnoutVariant(this.random.nextBoolean() ? this.getSnoutVariant() : other.getSnoutVariant());
            baby.setEarsVariant(this.random.nextBoolean() ? this.getEarsVariant() : other.getEarsVariant());
            baby.setLegsVariant(this.random.nextBoolean() ? this.getLegsVariant() : other.getLegsVariant());
//            baby.setPatternVariant(this.random.nextBoolean() ? this.getPatternVariant() : other.getPatternVariant());
            baby.setArmorVariant(0);
            baby.setTopHat(false);
            baby.setSupermanCape(false);
            UUID owner = this.getOwnerUUID();
            if (owner != null) {
                baby.setOwnerUUID(owner);
                baby.setTame(true);
            }
        }
        return baby;
    }

    public int getFurColor() { return entityData.get(FUR_COLOR); }
    public void setFurColor(int color) { entityData.set(FUR_COLOR, color); }
    public int getSnoutVariant() { return entityData.get(SNOUT_VARIANT); }
    public void setSnoutVariant(int variant) { entityData.set(SNOUT_VARIANT, variant); }
    public int getEarsVariant() { return entityData.get(EARS_VARIANT); }
    public void setEarsVariant(int variant) { entityData.set(EARS_VARIANT, variant); }
    public int getLegsVariant() { return entityData.get(LEGS_VARIANT); }
    public void setLegsVariant(int variant) { entityData.set(LEGS_VARIANT, variant); }
//    public int getPatternVariant() { return entityData.get(PATTERN_VARIANT); }
//    public void setPatternVariant(int variant) { entityData.set(PATTERN_VARIANT, variant); }
    public int getArmorVariant() { return entityData.get(ARMOR_VARIANT); }
    public void setArmorVariant(int variant) { entityData.set(ARMOR_VARIANT, variant); }
    public boolean hasTopHat() { return entityData.get(DATA_TOP_HAT); }
    public void setTopHat(boolean hasTopHat) { entityData.set(DATA_TOP_HAT, hasTopHat); }
    public boolean hasSupermanCape() { return entityData.get(DATA_SUPERMAN_CAPE); }
    public void setSupermanCape(boolean hasSupermanCape) { entityData.set(DATA_SUPERMAN_CAPE, hasSupermanCape); }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor lvl, DifficultyInstance diff, MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        super.finalizeSpawn(lvl, diff, reason, data, tag);
        int furColorMax = 10;
        int snoutVariantMax = 1;
        int earsVariantMax = 1;
        int legsVariantMax = 1;

        if (tag != null && tag.contains("FurColor")) {
            setFurColor(tag.getInt("FurColor"));
        } else {
            setFurColor(random.nextInt(furColorMax) + 1);
        }

        if (tag != null && tag.contains("SnoutVariant")) {
            setSnoutVariant(tag.getInt("SnoutVariant"));
        } else {
            setSnoutVariant(random.nextInt(snoutVariantMax) + 1);
        }

        if (tag != null && tag.contains("EarsVariant")) {
            setEarsVariant(tag.getInt("EarsVariant"));
        } else {
            setEarsVariant(random.nextInt(earsVariantMax) + 1);
        }

        if (tag != null && tag.contains("LegsVariant")) {
            setLegsVariant(tag.getInt("LegsVariant"));
        } else {
            setLegsVariant(random.nextInt(legsVariantMax) + 1);
        }

//        if (tag != null && tag.contains("PatternVariant")) {
//            setPatternVariant(tag.getInt("PatternVariant"));
//        } else {
//            if (random.nextFloat() < 0.2f) setPatternVariant(1);
//            else setPatternVariant(-1);
//        }

        if (tag != null && tag.contains("TopHat")) {
            setTopHat(tag.getBoolean("TopHat"));
        }

        if (this.isBaby()) {
            this.setAge(-48000); // 2 Minecraft days
        }

        return data;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("FurColor", getFurColor());
        tag.putInt("SnoutVariant", getSnoutVariant());
        tag.putInt("EarsVariant", getEarsVariant());
        tag.putInt("LegsVariant", getLegsVariant());
//        tag.putInt("PatternVariant", getPatternVariant());
        tag.putInt("ArmorVariant", getArmorVariant());
        tag.putBoolean("isSitting", this.isSitting());
        tag.putBoolean("TopHat", this.hasTopHat());

        CompoundTag inventoryTag = new CompoundTag();
        for (int i = 0; i < capybaraInventory.getContainerSize(); i++) {
            ItemStack stack = capybaraInventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                stack.save(itemTag);
                inventoryTag.put("Slot" + i, itemTag);
            }
        }
        tag.put("CapybaraInventory", inventoryTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setFurColor(tag.getInt("FurColor"));
        setSnoutVariant(tag.getInt("SnoutVariant"));
        setEarsVariant(tag.getInt("EarsVariant"));
        setLegsVariant(tag.getInt("LegsVariant"));
//        setPatternVariant(tag.getInt("PatternVariant"));
        if (tag.contains("ArmorVariant")) setArmorVariant(tag.getInt("ArmorVariant"));
        this.setSitting(tag.getBoolean("isSitting"));

        if (tag.contains("CapybaraInventory")) {
            CompoundTag inventoryTag = tag.getCompound("CapybaraInventory");
            for (int i = 0; i < capybaraInventory.getContainerSize(); i++) {
                if (inventoryTag.contains("Slot" + i)) {
                    ItemStack stack = ItemStack.of(inventoryTag.getCompound("Slot" + i));
                    capybaraInventory.setItem(i, stack);
                } else {
                    capybaraInventory.setItem(i, ItemStack.EMPTY);
                }
            }
        }

        ItemStack cosmeticSlot = capybaraInventory.getItem(0);
        if (cosmeticSlot.is(ModItems.CAPYBARA_TOP_HAT.get())) {
            setTopHat(true);
        } else {
            if (tag.contains("TopHat")) {
                setTopHat(tag.getBoolean("TopHat"));
            } else {
                setTopHat(false);
            }
//            if (cosmeticSlot.is(ModItems.CAPYBARA_SUPERMAN_CAPE.get())) {
//                setSupermanCape(true);
//            } else {
//                if (tag.contains("SupermanCape")) {
//                    setSupermanCape(tag.getBoolean("SupermanCape"));
//                } else {
//                    setSupermanCape(false);
//                }
//            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        int furDropCount = 1 + this.random.nextInt(2) + looting;
        for (int i = 0; i < furDropCount; i++) {
            ItemStack furDrop = getFurDropForColor(this.getFurColor());
            if (!furDrop.isEmpty()) {
                this.spawnAtLocation(furDrop);
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

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SUGAR_CANE);
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.PIG_AMBIENT; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) { return SoundEvents.PIG_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.PIG_DEATH; }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.SUGAR_CANE) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                FoodProperties foodProperties = itemstack.getFoodProperties(this);
                if (foodProperties != null) {
                    this.heal((float)foodProperties.getNutrition());
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    this.gameEvent(GameEvent.EAT, this);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            } else if (player.isShiftKeyDown() && this.isOwnedBy(player)) {
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
            } else {
                InteractionResult interaction = super.mobInteract(player, hand);
                if ((!interaction.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS;
                } else {
                    return interaction;
                }
            }
        } else if (itemstack.is(Items.SUGAR_CANE)) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource p_30386_, float p_30387_) {
        if (this.isInvulnerableTo(p_30386_)) {
            return false;
        } else {
            Entity entity = p_30386_.getEntity();
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }
            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                p_30387_ = (p_30387_ + 1.0F) / 2.0F;
            }
            return super.hurt(p_30386_, p_30387_);
        }
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            setHealth(20.0F);
        } else {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
    }

    public boolean isSitting() { return this.entityData.get(DATA_SITTING); }
    public void setSitting(boolean sitting) {
        this.entityData.set(DATA_SITTING, sitting);
        this.setOrderedToSit(sitting);
    }
    @Override
    public boolean isOrderedToSit() { return this.entityData.get(DATA_SITTING); }
    @Override
    public void setOrderedToSit(boolean sitting) { this.entityData.set(DATA_SITTING, sitting); }
}