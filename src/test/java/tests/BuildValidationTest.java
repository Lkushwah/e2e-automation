package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.models.Build;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class BuildValidationTest {
    @Test
    public void testBuildValidation() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/test/resources/builds.json");

        List<Build> builds = Arrays.asList(mapper.readValue(file, Build[].class));

        builds.stream();

    }
}
