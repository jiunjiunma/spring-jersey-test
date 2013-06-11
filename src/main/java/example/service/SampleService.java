package example.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import example.model.Sample;

/**
 * Date: 6/10/13
 * Time: 5:28 PM
 */
@Service
public class SampleService {
    private static final Map<Integer, Sample> samples = new HashMap<>();

    static {
        samples.put(1, new Sample(1, "sample1"));
        samples.put(2, new Sample(2, "sample2"));
    }

    public Sample getSample(int id) {
        return samples.get(id);
    }

    public Collection<Sample> getSamples() {
        return samples.values();
    }
}
