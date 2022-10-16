package neural.sampling;

import neural.exceptions.InvalidSampleException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public final class Sampling {

    private Sampling() {
    }

    public static void write(List<Sample> samples, String path) throws IOException {
        try (var writer = Files.newBufferedWriter(Path.of(path))) {
            for (Sample sample : samples) {
                writer.write(sample.toString());
                writer.newLine();
            }
        }
    }

    public static List<Sample> load(String path) throws IOException {
        return Files.lines(Path.of(path)).map(Sample::parse).collect(Collectors.toUnmodifiableList());
    }

    public static List<List<Sample>> partition(List<Sample> samples, int partitionSize) {
        return new AbstractList<>() {

            private final int sampleSize = samples.size();

            @Override
            public List<Sample> get(int index) {
                final int start = index * partitionSize;
                final int end = Math.min(start + partitionSize, sampleSize);
                return samples.subList(start, end);
            }

            @Override
            public int size() {
                return Math.toIntExact(Math.round(Math.ceil((double) sampleSize / partitionSize)));
            }
        };
    }

    public static List<List<Sample>> uniformPartition(List<Sample> samples, int sizePerClass) {
        List<List<Sample>> classes = groupByOneHot(samples).values().stream()
                .sorted(Comparator.comparingInt(List::size))
                .collect(Collectors.toList());

        int classCount = classes.size();
        int sampleSize = samples.size();
        int partitionSize = sizePerClass * classCount;
        int partitionCount = Math.toIntExact(Math.round(Math.ceil((double) sampleSize / partitionSize)));

        List<List<Sample>> partitions = new ArrayList<>(partitionCount);

        int spentClassesCount = 0;
        int totalRemaining = sampleSize;
        int[] indices = new int[classCount];
        for (int i = 0; i < partitionCount; i++) {
            int partitionRemaining = Math.min(partitionSize, totalRemaining);
            List<Sample> partition = new ArrayList<>(partitionRemaining);
            while (partitionRemaining > 0) {
                for (int j = spentClassesCount; j < classCount; j++) {
                    List<Sample> classSamples = classes.get(j);
                    int classSize = classSamples.size();
                    int currentIndex = indices[j];
                    for (int k = 0; k < sizePerClass || partitionRemaining > 0; k++) {
                        if (currentIndex == classSize) {
                            spentClassesCount++;
                            break;
                        }
                        partition.add(classSamples.get(currentIndex));
                        currentIndex++;
                        partitionRemaining--;
                        totalRemaining--;
                    }
                    indices[j] = currentIndex;
                }
            }
            partitions.add(partition);
        }
        return partitions;
    }

    public static Map<Integer, List<Sample>> groupByOneHot(List<Sample> samples) {
        return samples.stream().collect(Collectors.groupingBy(sample -> oneHotDecode(sample.y())));
    }

    public static int oneHotDecode(double[] encoded) {
        int index = 0;
        for (double x : encoded) {
            if (x == 1.) return index;

            if (x != 0.) throw new InvalidSampleException();

            index++;
        }
        return -1;
    }

    public static double[] oneHotEncode(int classification, int totalClassCount) {
        double[] oneHot = new double[totalClassCount];
        oneHot[classification] = 1.;
        return oneHot;
    }

    public static int argMax(double[] array) {
        int index = 0;
        double max = array[index];
        for (int i = 1, n = array.length; i < n; i++) {
            var temp = array[i];
            if (max < temp) {
                max = temp;
                index = i;
            }
        }
        return index;
    }
}
