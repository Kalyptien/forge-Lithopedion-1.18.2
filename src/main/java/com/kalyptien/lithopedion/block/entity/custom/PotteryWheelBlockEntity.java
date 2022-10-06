package com.kalyptien.lithopedion.block.entity.custom;

import com.kalyptien.lithopedion.block.entity.ModBlockEntities;
import com.kalyptien.lithopedion.item.ModItems;
import com.kalyptien.lithopedion.recipe.PotteryWheelRecipe;
import com.kalyptien.lithopedion.screen.PotteryWheelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;

public class PotteryWheelBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    private int waterLVL = 0;
    private int maxWaterLVL = 18;

    private int clayLVL = 0;
    private int maxClayLVL = 18;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public PotteryWheelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.POTTERY_WHEEL_BLOCK_ENTITY.get(), pPos, pBlockState);;


        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return PotteryWheelBlockEntity.this.progress;
                    case 1: return PotteryWheelBlockEntity.this.maxProgress;

                    case 2: return PotteryWheelBlockEntity.this.clayLVL;
                    case 3: return PotteryWheelBlockEntity.this.maxClayLVL;

                    case 4: return PotteryWheelBlockEntity.this.waterLVL;
                    case 5: return PotteryWheelBlockEntity.this.maxWaterLVL;

                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: PotteryWheelBlockEntity.this.progress = value; break;
                    case 1: PotteryWheelBlockEntity.this.maxProgress = value; break;

                    case 2: PotteryWheelBlockEntity.this.clayLVL = value; break;
                    case 3: PotteryWheelBlockEntity.this.maxClayLVL = value; break;

                    case 4: PotteryWheelBlockEntity.this.waterLVL = value; break;
                    case 5: PotteryWheelBlockEntity.this.maxWaterLVL = value; break;
                }
            }

            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Pottery Wheel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {

        return new PotteryWheelMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {

        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("pottery_wheel.progress", progress);
        tag.putInt("pottery_wheel.waterLVL", waterLVL);
        tag.putInt("pottery_wheel.clayLVL", clayLVL);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("pottery_wheel.progress");
        waterLVL = nbt.getInt("pottery_wheel.waterLVL");
        clayLVL = nbt.getInt("pottery_wheel.clayLVL");
    }

    public void drops() {

        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PotteryWheelBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {

            if (enoughWater(pBlockEntity) && enoughClay(pBlockEntity)){
                pBlockEntity.progress++;
                if(Math.random() <= 0.05){
                    pBlockEntity.waterLVL--;
                }
                setChanged(pLevel, pPos, pState);
                if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                    craftItem(pBlockEntity);
                }
            }
            else if (hasClayInClaySlot(pBlockEntity) && (pBlockEntity.clayLVL + (pBlockEntity.maxClayLVL / 4)) < pBlockEntity.maxClayLVL) {
                stockClay(pBlockEntity);
                setChanged(pLevel, pPos, pState);
            }
            else if (hasWaterInWaterSlot(pBlockEntity) && (pBlockEntity.waterLVL + (pBlockEntity.maxWaterLVL / 4)) < pBlockEntity.maxWaterLVL) {
                stockWater(pBlockEntity);
                setChanged(pLevel, pPos, pState);
            }
            else {
                pBlockEntity.resetProgress();
                setChanged(pLevel, pPos, pState);
            }
        }
        else if (hasClayInClaySlot(pBlockEntity) && (pBlockEntity.clayLVL + (pBlockEntity.maxClayLVL / 4)) < pBlockEntity.maxClayLVL) {
            stockClay(pBlockEntity);
            setChanged(pLevel, pPos, pState);
        }
        else if (hasWaterInWaterSlot(pBlockEntity) && (pBlockEntity.waterLVL + (pBlockEntity.maxWaterLVL / 4)) < pBlockEntity.maxWaterLVL) {
            stockWater(pBlockEntity);
            setChanged(pLevel, pPos, pState);
        }
        else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(PotteryWheelBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PotteryWheelRecipe> match = level.getRecipeManager()
                .getRecipeFor(PotteryWheelRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static boolean hasClayInClaySlot(PotteryWheelBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(4).getItem() == Items.CLAY_BALL;
    }

    private static boolean hasWaterInWaterSlot(PotteryWheelBlockEntity entity) {
        return PotionUtils.getPotion(entity.itemHandler.getStackInSlot(5)) == Potions.WATER;
    }

    private static boolean enoughClay(PotteryWheelBlockEntity entity) {
        return entity.clayLVL > 0;
    }

    private static boolean enoughWater(PotteryWheelBlockEntity entity) {
        return entity.waterLVL > 0;
    }

    private static void stockClay(PotteryWheelBlockEntity pBlockEntity){
        pBlockEntity.clayLVL = pBlockEntity.clayLVL + pBlockEntity.maxClayLVL / 4;
        pBlockEntity.itemHandler.extractItem(4,1, false);
    }

    private static void stockWater(PotteryWheelBlockEntity pBlockEntity){
        pBlockEntity.waterLVL = pBlockEntity.waterLVL + pBlockEntity.maxWaterLVL / 2;
        pBlockEntity.itemHandler.extractItem(5,1, false);
        pBlockEntity.itemHandler.setStackInSlot(5, new ItemStack(Items.GLASS_BOTTLE, 1));
    }

    private static void craftItem(PotteryWheelBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<PotteryWheelRecipe> match = level.getRecipeManager()
                .getRecipeFor(PotteryWheelRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(2,1, false);

            entity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(3).getCount() + 1));

            entity.clayLVL = entity.clayLVL - (int)(Math.random() * ( (entity.maxClayLVL / 4) - 1 ));
            if(entity.clayLVL < 0){
                entity.clayLVL = 0;
            }
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
