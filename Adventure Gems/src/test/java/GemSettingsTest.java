import com.caved_in.adventurecraft.gems.item.ChancedEnchantment;
import com.caved_in.adventurecraft.gems.item.GemPrefixSettings;
import com.caved_in.adventurecraft.gems.item.GemSettings;
import com.caved_in.adventurecraft.gems.item.GemSuffixSettings;
import org.bukkit.enchantments.Enchantment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GemSettingsTest {
    
    @Test
    public void testGemCreationEnchantmentCheck() {
        GemSettings gemSettings = new GemSettings();
        
        int percent = 100;
        
        ChancedEnchantment damageAllChanced = new ChancedEnchantment().enchant(Enchantment.DAMAGE_ALL,2).chance(percent);
        
        gemSettings.addEnchantment(damageAllChanced).addEnchantment(
                new ChancedEnchantment().enchant(Enchantment.ARROW_DAMAGE,2).chance(20)
        ).addEnchantment(
                new ChancedEnchantment().enchant(Enchantment.FIRE_ASPECT, 1).chance(10)
        );
        
        assertTrue(gemSettings.hasEnchantment(Enchantment.DAMAGE_ALL));
        assertTrue(gemSettings.hasEnchantment(Enchantment.FIRE_ASPECT));
        assertTrue(gemSettings.hasEnchantment(Enchantment.ARROW_DAMAGE,2));

        ChancedEnchantment randomEnchant = gemSettings.getRandomEnchantment();
        
        if (randomEnchant.getEnchantment().equals(Enchantment.DAMAGE_ALL)) {
            assertTrue(randomEnchant.getLevel() == 2);
        } else if (randomEnchant.getEnchantment().equals(Enchantment.ARROW_DAMAGE)) {
            assertTrue(randomEnchant.getLevel() == 2);
        } else {
            assertTrue(randomEnchant.getLevel() == 1);
        }
    }
    
    @Test
    public void randomEnchantsTest() {
        Set<Enchantment> enchants = new HashSet<>();
        
        GemSettings gemSettings = new GemSettings();
        
        int percent = 100;

        ChancedEnchantment damageAllChanced = new ChancedEnchantment().enchant(Enchantment.DAMAGE_ALL,2).chance(percent);

        gemSettings.addEnchantment(damageAllChanced).addEnchantment(
                new ChancedEnchantment().enchant(Enchantment.ARROW_DAMAGE,2).chance(20)
        ).addEnchantment(
                new ChancedEnchantment().enchant(Enchantment.FIRE_ASPECT, 1).chance(10)
        );
        
        while (enchants.size() < 3) {
            enchants.add(gemSettings.getRandomEnchantment().getEnchantment());
        }
        
        assertTrue(enchants.contains(Enchantment.ARROW_DAMAGE));
        assertTrue(enchants.contains(Enchantment.FIRE_ASPECT));
        assertTrue(enchants.contains(Enchantment.DAMAGE_ALL));
    }
    
    @Test
    public void gemSettingsPrefixTest() {
        GemSettings gemSettings = new GemSettings();
        int percent = 100;
        
        gemSettings.defaultPrefixData();
        
        //Verify the gemsettings has prefixes for levels 1 - 5
        for(int i = 1; i <= 5; i++) {
            assertTrue(gemSettings.hasPrefixForLevel(i));
        }
        
        //Manually check if all the prefixes are proper
        assertEquals(gemSettings.getPrefixForLevel(1),"Crude");
        assertEquals(gemSettings.getPrefixForLevel(2),"Cracked");
        assertEquals(gemSettings.getPrefixForLevel(3),"Refined");
        assertEquals(gemSettings.getPrefixForLevel(4),"Polished");
        assertEquals(gemSettings.getPrefixForLevel(5),"Untainted");
        assertEquals(gemSettings.getPrefixForLevel(10001),"Perfected");

        gemSettings.clearPrefixData().addPrefixData(GemPrefixSettings.of("Cranked",1,Integer.MAX_VALUE));
        assertEquals(gemSettings.getPrefixForLevel(1000), "Cranked");
        assertEquals(gemSettings.getPrefixForLevel(1),"Cranked");
    }
    
    @Test
    public void gemSettingsTestSuffixData() {
        GemSettings settings = new GemSettings();
        
        settings.addSuffixData(GemSuffixSettings.of(Enchantment.DAMAGE_ALL, "Strength"));
        
        assertTrue(settings.hasSuffixForEnchantment(Enchantment.DAMAGE_ALL));

        settings.clearSuffixFor(Enchantment.DAMAGE_ALL);
        
        settings.addSuffixData(GemSuffixSettings.of(Enchantment.DAMAGE_ALL,"Strength",1,4));
        assertFalse(settings.hasSuffixForEnchantment(Enchantment.DAMAGE_ALL,5));
        
        String suffix = settings.getSuffixFor(Enchantment.DAMAGE_ALL,4);
        
        assertThat(suffix).isEqualToIgnoringCase("Strength");
    }
    
    @Test
    public void gemSettingsDefaultEnchants() {
        GemSettings settings = new GemSettings();
        
        Enchantment[] enchants = {Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE, Enchantment.ARROW_KNOCKBACK, Enchantment.DURABILITY};
        settings.defaultEnchantsFor(enchants);
        
        for(Enchantment enchantment : enchants) {
            assertTrue(settings.hasEnchantment(enchantment));
        }
        
    }
}
