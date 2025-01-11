package net.ramixin.ramchants.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.Weighting;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Optional;

public final class LinkedCursesEntry {

    public static final Codec<LinkedCursesEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LinkedCurse.CODEC.listOf().fieldOf("values").forGetter(LinkedCursesEntry::linkedCurses)
    ).apply(instance, LinkedCursesEntry::new));

    public static final LinkedCursesEntry DEFAULT = new LinkedCursesEntry(List.of());

    private final List<LinkedCurse> linkedCurses;

    private LinkedCursesEntry(List<LinkedCurse> linkedCurses) {
        this.linkedCurses = linkedCurses;
    }

    public List<LinkedCurse> linkedCurses() {
        return linkedCurses;
    }

    @Override
    public String toString() {
        return "LinkedCurseEntry[" +
                "linkedCurse=" + linkedCurses + ']';
    }

    public static LinkedCursesEntry getLinkedCurses(DynamicRegistryManager registryManager, RegistryKey<Enchantment> enchantmentKey) {
        Registry<LinkedCursesEntry> registry = registryManager.getOrThrow(ModRegistries.LINKED_CURSES);
        Optional<LinkedCursesEntry> maybeCurse = registry.getOptionalValue(RegistryKey.of(ModRegistries.LINKED_CURSES, enchantmentKey.getValue()));
        return maybeCurse.orElse(DEFAULT);
    }

    public Optional<RegistryEntry<Enchantment>> getRandomCurse(Random random) {
        Optional<LinkedCurse> maybeCurse = Weighting.getRandom(random, linkedCurses);
        return maybeCurse.map(LinkedCurse::linkedEnchantment);
    }

}
