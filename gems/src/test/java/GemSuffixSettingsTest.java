import com.caved_in.adventurecraft.gems.item.GemSuffixSettings;
import com.caved_in.adventurecraft.gems.item.LevelDescriptor;
import com.google.common.collect.Sets;
import org.bukkit.enchantments.Enchantment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GemSuffixSettingsTest {
    
    @Test
    public void testSuffixDescriptors() {
        //Create a basic suffix-settings class for DAMAGE_ALL, tagged with "Strength"
        GemSuffixSettings suffixSettings = GemSuffixSettings.of(Enchantment.DAMAGE_ALL,"Strength");
        
        suffixSettings.addDescriptor(1,"Weak","Shoddy","Poor")
                .addDescriptor(2, "Enforced", "Great", "Heavy")
                .addDescriptor(3, "Brutal", "Barbaric", "Grotesque");
        
        String suffix = suffixSettings.getSuffix(1);
        
        assertThat(suffix).contains(" ");
        
        String[] split = suffix.split(" ");
        assertThat(Sets.newHashSet("Weak","Shoddy","Poor")).contains(split[0]);
        assertThat(split[1]).isEqualToIgnoringCase("Strength");
        System.out.println(suffix);
    }
}
