import com.caved_in.adventurecraft.gems.item.ChancedEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ChancedEnchantmentTest {
    
    @Test
    public void chancedEnchantmentTestOptionalTuple() {
        
        ChancedEnchantment arrowLvlOne = new ChancedEnchantment()
                .chance(100)
                .enchant(Enchantment.ARROW_FIRE,1);
        
        Enchantment arrowFire = arrowLvlOne.getEnchantment();
        int levelOne = arrowLvlOne.getLevel();
        
        assertTrue(arrowFire == Enchantment.ARROW_FIRE);
        
        assertTrue(levelOne == 1);
        
        arrowLvlOne.enchant(Enchantment.ARROW_DAMAGE,2);
        
        assertTrue(arrowLvlOne.getEnchantment() == Enchantment.ARROW_DAMAGE && arrowLvlOne.getLevel() == 2);
        
        Optional<Pair<Enchantment, Integer>> enchantInfo = arrowLvlOne.getOptionalEnchantmentTuple();

        assertTrue(enchantInfo.isPresent());
        
        enchantInfo = arrowLvlOne.chance(50).enchant(Enchantment.PROTECTION_PROJECTILE,4).getOptionalEnchantmentTuple();
        
        if (enchantInfo.isPresent()) {
            System.out.println("Enchantment Info is Present! 2 Extra Assertions");

            assertTrue(enchantInfo.get().getValue0() == Enchantment.PROTECTION_PROJECTILE);
            assertTrue(enchantInfo.get().getValue1() == 4);
        }
        
        String aliasProtProj4 = arrowLvlOne.getEnchantName();
    }
    
}
