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
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if(itemstack.getDescriptionId().equals("item.lithopedion.pottery_staff_fast")){
            pPlayer.setItemInHand(pUsedHand, new ItemStack(ModItems.POTTERY_STAFF_MEDIUM.get(), 1));
        }
        else if(itemstack.getDescriptionId().equals("item.lithopedion.pottery_staff_medium")){
            pPlayer.setItemInHand(pUsedHand, new ItemStack(ModItems.POTTERY_STAFF_SLOW.get(), 1));
        }
        else if(itemstack.getDescriptionId().equals("item.lithopedion.pottery_staff_slow")){
            pPlayer.setItemInHand(pUsedHand, new ItemStack(ModItems.POTTERY_STAFF_FAST.get(), 1));
        }
        else{
            System.out.println(itemstack.getDescriptionId());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
