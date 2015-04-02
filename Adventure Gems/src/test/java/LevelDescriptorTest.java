import com.caved_in.adventurecraft.gems.item.LevelDescriptor;
import org.apache.commons.lang.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LevelDescriptorTest {
    
    @Test
    public void test() {
        String[] descriptionsLevelOne = {
                "Weak","Poor","Scrubs","Lesser"
        };
        
        //todo idea make it so if it has "hidden" in it's name, it hides the enchantments, if possible.

        String[] descriptionsLevelTwo = {
                "Slight","Humble","Lowly"
        };
        
        LevelDescriptor descriptor = new LevelDescriptor();

        descriptor.level(1,descriptionsLevelOne).level(2,descriptionsLevelTwo);
        
        String randomDescriptorLvlOne = descriptor.forLevel(1);
        String randomDescriptorLvlTwo = descriptor.forLevel(2);
        
        String nullDescriptor = descriptor.forLevel(3);
        
        assert(nullDescriptor == null);
        
        assert (ArrayUtils.contains(descriptionsLevelOne,randomDescriptorLvlOne));
        
        assert (ArrayUtils.contains(descriptionsLevelTwo,randomDescriptorLvlTwo));
        
        assertThat(descriptor.allForLevel(1)).contains(descriptionsLevelOne);
        assertThat(descriptor.allForLevel(2)).contains(descriptionsLevelTwo);
        
    }
    
}
