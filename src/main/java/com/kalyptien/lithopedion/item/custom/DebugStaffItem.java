package com.kalyptien.lithopedion.item.custom;

import com.kalyptien.lithopedion.block.ModBlocks;
import com.kalyptien.lithopedion.block.custom.SanctuaryAutelBlock;
import com.kalyptien.lithopedion.block.custom.SanctuaryStatusBlock;
import com.kalyptien.lithopedion.block.custom.SanctuaryStoneBlock;
import com.kalyptien.lithopedion.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class DebugStaffItem extends Item {

    public DebugStaffItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()) {

            BlockPos positionClicked = pContext.getClickedPos();
            Block block = pContext.getLevel().getBlockState(positionClicked).getBlock();
            Player player = pContext.getPlayer();

            if(block instanceof SanctuaryStatusBlock sanctuary) {
                assert player != null;
                player.sendMessage(new TextComponent( sanctuary.getSvariant() + "'s Status"), player.getUUID());
                if(sanctuary.getSanctuary() != null){
                    player.sendMessage(new TextComponent( "Linked sanctuary : " + sanctuary.getSanctuary().getDescriptionId() + " of type " + sanctuary.getSanctuary().getSvariant()), player.getUUID());
                }
            }
            else if(block instanceof SanctuaryStoneBlock sanctuary){
                assert player != null;
                player.sendMessage(new TextComponent(sanctuary.getSvariant() + "'s Stone"), player.getUUID());
                if(sanctuary.getSanctuary() != null){
                    player.sendMessage(new TextComponent( "Linked sanctuary : " + sanctuary.getSanctuary().getDescriptionId() + " of type " + sanctuary.getSanctuary().getSvariant()), player.getUUID());

                }
            }
            else if(block instanceof SanctuaryAutelBlock sanctuary){
                assert player != null;
                player.sendMessage(new TextComponent(sanctuary.getSvariant() + "'s Autel"), player.getUUID());

                player.sendMessage(new TextComponent("Linked Blocks : " + sanctuary.getBlocks().size()), player.getUUID());
                for (int i = 0 ; i < sanctuary.getBlocks().size(); i++ ){
                    player.sendMessage(new TextComponent(i + " " + sanctuary.getBlocks().get(i).getDescriptionId() + " of type " + sanctuary.getBlocks().get(i).getSvariant()), player.getUUID());
                }
                player.sendMessage(new TextComponent("==="), player.getUUID());

                player.sendMessage(new TextComponent("Linked Childrens : " + sanctuary.getChildrens().size()), player.getUUID());
                for (int j = 0 ; j < sanctuary.getChildrens().size(); j++ ){
                    player.sendMessage(new TextComponent(j + " " + sanctuary.getChildrens().get(j).getId() + " of type " + sanctuary.getChildrens().get(j).getVariant()), player.getUUID());
                }
                player.sendMessage(new TextComponent("==="), player.getUUID());

                player.sendMessage(new TextComponent("Linked Soldiers : " + sanctuary.getSoldiers().size()), player.getUUID());
                for (int u = 0 ; u < sanctuary.getSoldiers().size(); u++ ){
                    player.sendMessage(new TextComponent(u + " " + sanctuary.getSoldiers().get(u).getId() + " of type " + sanctuary.getSoldiers().get(u).getVariant()), player.getUUID());
                }
            }
        }

        return super.useOn(pContext);
    }
}