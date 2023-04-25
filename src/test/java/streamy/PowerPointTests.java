package streamy;

import org.testng.Assert;
import org.testng.annotations.Test;
import streamy.model.CustomModel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PowerPointTests {

    @Test
    public void map() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value1", "value2"), 1),
                new CustomModel("name2", Arrays.asList("value3", "value4"), 2),
                new CustomModel("name3", Arrays.asList("value5", "value6"), 3)
        );

        Stream<String> mappedStreamName = model.stream().map(CustomModel::getName);
        Stream<List<String>> mappedStreamValues = model.stream().map(CustomModel::getValues);
        Stream<Integer> mappedStreamNumber = model.stream().map(CustomModel::getNumber);

        List<String> mappedName = mappedStreamName.collect(Collectors.toList());
        List<List<String>> mappedValues = mappedStreamValues.collect(Collectors.toList());
        List<Integer> mappedNumber = mappedStreamNumber.collect(Collectors.toList());

        System.out.println(mappedName);
        System.out.println(mappedValues);
        System.out.println(mappedNumber);
    }

    @Test
    public void filter() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value1", "value2"), 1),
                new CustomModel("name2", Arrays.asList("value3", "value4"), 2),
                new CustomModel("name3", Arrays.asList("value5", "value6"), 3)
        );

        Stream<CustomModel> simpleStream = model.stream();
        Stream<CustomModel> filteredStreamName1 = model.stream().filter(data -> data.getName().equals("name1"));
        Stream<CustomModel> filteredStreamValue3 = model.stream().filter(e -> e.getValues().contains("value3"));
        Stream<CustomModel> filteredStreamNumber3 = model.stream().filter(whatever -> whatever.getNumber() == 3);

        List<CustomModel> simpleResult = simpleStream.collect(Collectors.toList());
        List<CustomModel> filteredName1 = filteredStreamName1.collect(Collectors.toList());
        List<CustomModel> filteredValue3 = filteredStreamValue3.collect(Collectors.toList());
        List<CustomModel> filteredNumber3 = filteredStreamNumber3.collect(Collectors.toList());

        System.out.println(simpleResult);
        System.out.println(filteredName1);
        System.out.println(filteredValue3);
        System.out.println(filteredNumber3);
    }

    @Test
    public void distinct() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value1", "value2"), 1),
                new CustomModel("name1", Arrays.asList("value1", "value2"), 1),
                new CustomModel("name1", Arrays.asList("value5", "value6"), 3)
        );

        Stream<CustomModel> uniqueValuesStream = model.stream().distinct();
        List<CustomModel> uniqueValues = uniqueValuesStream.collect(Collectors.toList());

        System.out.println(uniqueValues);
    }

    @Test
    public void sort() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value1", "value2"), 3),
                new CustomModel("name2", Arrays.asList("value3", "value3"), 2),
                new CustomModel("name3", Arrays.asList("value2", "value1"), 1)
        );

        Stream<CustomModel> sortedByNumberStream = model.stream().sorted(Comparator.comparingInt(CustomModel::getNumber));
        Stream<CustomModel> sortedByStringStream = model.stream().sorted(Comparator.comparing(CustomModel::getName));
        Stream<CustomModel> sortedByCustomConditionStream = model.stream().sorted(
                (h1, h2) -> h2.getValues().get(0).compareTo(h1.getValues().get(0))
        );

        List<CustomModel> sortedByNumber = sortedByNumberStream.collect(Collectors.toList());
        List<CustomModel> sortedByString = sortedByStringStream.collect(Collectors.toList());
        List<CustomModel> sortedByCustomCondition = sortedByCustomConditionStream.collect(Collectors.toList());

        System.out.println(sortedByNumber);
        System.out.println(sortedByString);
        System.out.println(sortedByCustomCondition);
    }

    @Test
    public void match() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value10", "value2"), 0),
                new CustomModel("name2", Arrays.asList("value3", "value4"), 0),
                new CustomModel("name3", Arrays.asList("value56", "value6"), 0)
        );

        boolean isAvailable = model.stream().anyMatch(currentElement -> currentElement.getName().equals("name1"));
        boolean onlyExpectedValue = model.stream().allMatch(e -> e.getNumber() == 0);

        System.out.println(isAvailable);
        System.out.println(onlyExpectedValue);
    }

    @Test
    public void optional() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value10", "value2"), 0),
                new CustomModel("name2", Arrays.asList("value3", "value4"), 0),
                new CustomModel("name3", Arrays.asList("value56", "value6"), 0)
        );

        Optional<CustomModel> mayNotExist1 = model.stream().filter(e -> e.getNumber() == 1).findFirst();
        Optional<CustomModel> mayNotExist2 = model.stream().filter(e -> e.getNumber() == 2).findAny();

        Optional<Integer> existIfModelNotEmpty1 = model.stream().map(CustomModel::getNumber).findFirst();
        Optional<CustomModel> existIfModelNotEmpty2 = model.stream().findAny();

        CustomModel foundOrEmpty1 = mayNotExist1.orElseGet(
                () -> mayNotExist1.orElse(new CustomModel("empty1", List.of(), 0))
        );

        CustomModel foundOrEmpty2 = mayNotExist1.orElseGet(
                () -> mayNotExist2.orElse(new CustomModel("empty2", List.of(), 0))
        );

        Integer exist1 = existIfModelNotEmpty1.get();
        CustomModel exists2 = existIfModelNotEmpty2.get();

        System.out.println(foundOrEmpty1);
        System.out.println(foundOrEmpty2);
        System.out.println(exist1);
        System.out.println(exists2);
    }

    @Test
    public void arrayAndOthers() {
        List<CustomModel> model = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value10", "value2"), 0),
                new CustomModel("name1", Arrays.asList("value3", "value4"), 1),
                new CustomModel("name3", Arrays.asList("value56", "value6"), 3)
        );

        CustomModel[] array = model.stream().filter(e -> e.getName().equals("name1")).toArray(CustomModel[]::new);
        int sum = model.stream().mapToInt(CustomModel::getNumber).sum();
        Map<String, List<CustomModel>> mapByName = model.stream().collect(Collectors.groupingBy(CustomModel::getName));

        System.out.println(Arrays.toString(array));
        System.out.println(sum);
        System.out.println(mapByName);
    }

    @Test
    public void compare() {
        List<CustomModel> modelA = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value1", "value2"), 1),
                new CustomModel("name2", Arrays.asList("value3", "value4"), 2)//,
//                new CustomModel("nameA1", Arrays.asList("valueA1", "valueA2"), 10),
//                new CustomModel("nameA2", Arrays.asList("valueA3", "valueA4"), 20)
        );

        List<CustomModel> modelB = Arrays.asList(
                new CustomModel("name1", Arrays.asList("value1", "value2"), 1),
                new CustomModel("name2", Arrays.asList("value3", "value4"), 2)//,
//                new CustomModel("nameB1", Arrays.asList("valueB1", "valueB2"), 11)
        );

        List<CustomModel> commons = modelA.stream().filter(modelB::contains).collect(Collectors.toList());
        Assert.assertEquals(commons.size() + modelB.size(), commons.size() + modelA.size());

        List<CustomModel> differences1 = modelA.stream().filter(element -> !modelB.contains(element)).collect(Collectors.toList());
        List<CustomModel> differences2 = modelB.stream().filter(element -> !modelA.contains(element)).collect(Collectors.toList());
        Assert.assertTrue(differences1.size() == differences2.size() && differences1.size() == 0);
    }
}
