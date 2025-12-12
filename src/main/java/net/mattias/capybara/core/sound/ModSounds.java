package net.mattias.capybara.core.sound;

import net.mattias.capybara.Capybara;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Capybara.MOD_ID);

    public static final RegistryObject<SoundEvent> CAPYBARA_HURT = registerSoundEvent("capybara_hurt");
    public static final RegistryObject<SoundEvent> CAPYBARA_DEATH = registerSoundEvent("capybara_death");
    public static final RegistryObject<SoundEvent> CAPYBARA_AMBIENT = registerSoundEvent("capybara_ambient");
    public static final RegistryObject<SoundEvent> CAPYBARA_AMBIENT_2 = registerSoundEvent("capybara_ambient_2");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () ->
                SoundEvent.createVariableRangeEvent(new ResourceLocation(Capybara.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
