package com.kalyptien.lithopedion.item.custom;

import com.kalyptien.lithopedion.Lithopedion;
import com.kalyptien.lithopedion.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PotteryStaffItem extends Item {
    public PotteryStaffItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Item item = pPlayer.getItemInHand(pUsedHand).getItem();
        if(item == ModItems.POTTERY_STAFF_FAST.get()){
            pPlayer.setItemInHand(pUsedHand, new ItemStack(ModItems.POTTERY_STAFF_MEDIUM.get(), 1));
        }
        else if(item == ModItems.POTTERY_STAFF_MEDIUM.get()){
            pPlayer.setItemInHand(pUsedHand, new ItemStack(ModItems.POTTERY_STAFF_SLOW.get(), 1));
        }
        else if(item == ModItems.POTTERY_STAFF_SLOW.get()){
            pPlayer.setItemInHand(pUsedHand, new ItemStack(ModItems.POTTERY_STAFF_FAST.get(), 1));
        }
        else{
            throw new IllegalArgumentException("Invalid pottery_staff type");
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
