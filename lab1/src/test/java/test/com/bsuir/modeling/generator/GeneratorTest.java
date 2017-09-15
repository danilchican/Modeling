package test.com.bsuir.modeling.generator;

import static com.bsuir.modeling.data.Printer.print;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import com.bsuir.modeling.generator.Generator;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {

    private Generator generator;
    private List<Double> expectedList;

    @Before
    public void setUp() {
        generator = new Generator(3, 5, 1);

        expectedList = new ArrayList<Double>() {{
            add(0.6);
            add(0.8);
            add(0.4);
            add(0.2);
            add(0.6);
        }};
    }

    @Test
    public void isValidValuesTest() {
        boolean expected = generator.isValidValues();
        Assert.assertTrue(expected);
    }

    @Test
    public void getRandomNumberTest() {
        generator.generateValues();
        List<Double> actualList = generator.getValues();

        print("Generator values: " + actualList);
        Assert.assertThat(expectedList, contains(actualList.toArray()));
    }

    @Test
    public void calculateMxTest() {
        generator.generateValues();

        double actualMx = generator.calculateMx();
        double expectedMx = 0.52;

        Assert.assertThat(actualMx, Matchers.is(expectedMx));
    }

    @Test
    public void calculateDxTest() {
        generator.generateValues();

        double actualDx = generator.calculateDx();
        double expectedDx = 0.052;

        Assert.assertThat(actualDx, Matchers.is(expectedDx));
    }

    @Test
    public void calculateStdTest() {
        generator.generateValues();

        double actualStd = generator.calculateStd();
        double expectedStd = 0.2280;

        Assert.assertThat(actualStd, Matchers.is(expectedStd));
    }
}
