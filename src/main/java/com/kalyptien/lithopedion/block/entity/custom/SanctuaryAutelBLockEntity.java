package com.kalyptien.lithopedion.block.entity.custom;

import com.google.common.collect.Lists;
import com.kalyptien.lithopedion.block.custom.SanctuaryBlock;
import com.kalyptien.lithopedion.block.entity.ModBlockEntities;
import com.kalyptien.lithopedion.entity.custom.ChildrenEntity;
import com.kalyptien.lithopedion.entity.custom.SoldierEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SanctuaryAutelBLockEntity extends BlockEntity {

    private List<BlockPos> blocksPos = Lists.newArrayList();
    private int soulLimiter = 5;
    private int soulCount = 0;
    private int soulCooldown = 24000;

    public SanctuaryAutelBLockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SANCTUARY_AUTEL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    private boolean hasSavedBlockPos() {
        return this.blocksPos.size() == 0;
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.blocksPos = null;
        if (pTag.contains("BlocksPos")) {
            ListTag listtag = pTag.getList("BlocksPos", 10);
            for (int i = 0; i < listtag.size(); ++i) {
                this.blocksPos.add(listtag.getCompound(i).getCompound("BP"));
            }
        }

        soulLimiter = pTag.getInt("sanctuary.SoulLimiter");
        soulCount = pTag.getInt("sanctuary.SoulCount");
        soulCooldown = pTag.getInt("sanctuary.SoulCooldown");

    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        if (this.hasSavedBlockPos()) {
            ListTag listtag = new ListTag();
            for(BlockPos block : this.blocksPos) {
                CompoundTag compoundtag = NbtUtils.writeBlockPos(block);
                CompoundTag compoundtag1 = new CompoundTag();
                compoundtag1.put("BP", compoundtag);
                listtag.add(compoundtag1);
            }
            pTag.put("sanctuary.BlocksPos", listtag);
        }
        pTag.putInt("sanctuary.SoulLimiter", this.getSoulLimiter());
        pTag.putInt("sanctuary.SoulCount", this.getSoulCount());
        pTag.putInt("sanctuary.SoulCooldown", this.getSoulCooldown());

    }

    public void updateSoulLimiter(int limiter){
            this.soulLimiter = limiter;
    }

    public int getSoulLimiter() {
        return soulLimiter;
    }
    public void setSoulLimiter(int soulLimiter) {
        this.soulLimiter = soulLimiter;
    }

    public int getSoulCount() {
        return soulCount;
    }
    public void setSoulCount(int soulCount) {
        this.soulCount = soulCount;
    }

    public int getSoulCooldown() {
        return soulCooldown;
    }
    public void setSoulCooldown(int soulCooldown) {
        this.soulCooldown = soulCooldown;
    }

    public List<BlockPos> getBlocks() {
        return blocksPos;
    }
    public void setBlocks(List<BlockPos> blocks) {
        this.blocksPos = blocks;
    }


}
