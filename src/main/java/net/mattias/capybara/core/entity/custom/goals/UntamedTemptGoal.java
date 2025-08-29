package net.mattias.capybara.core.entity.custom.goals;

import net.mattias.capybara.core.entity.custom.CapybaraEntity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class UntamedTemptGoal extends TemptGoal {
    public final CapybaraEntity capy;


    public UntamedTemptGoal(CapybaraEntity capy, double speed) {
        super(capy, speed, Ingredient.of(Items.SUGAR_CANE), false);
        this.capy = capy;
    }

    @Override public boolean canUse(){
        return !capy.isTame() && super.canUse();
    }
    @Override public boolean canContinueToUse(){
        return !capy.isTame() && super.canContinueToUse();
    }
}