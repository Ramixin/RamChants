package net.ramixin.ramchants.util;

@FunctionalInterface
public interface ModEffectConsumer<T,K,M,N> {

    void accept(EnchantmentDuck enchantment, T t, K k, M m, N n);

}
