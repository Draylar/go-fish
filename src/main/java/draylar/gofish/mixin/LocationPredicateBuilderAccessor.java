package draylar.gofish.mixin;

import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LocationPredicate.Builder.class)
public interface LocationPredicateBuilderAccessor {
    @Accessor("y")
    void setY(NumberRange.FloatRange y);
}
