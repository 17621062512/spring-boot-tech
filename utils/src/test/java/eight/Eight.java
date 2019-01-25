package eight;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Eight {
    public static void main(String[] args) {
        List<List<Integer>> a = new ArrayList<>();
        List<Integer> aaa = Lists.newArrayList(1, 2, 3, 4);
        List<Integer> bbb = Lists.newArrayList(1, 2, 3, 4);
        List<Integer> ccc = Lists.newArrayList(1, 2, 3, 4);
        a.add(aaa);
        a.add(bbb);
        a.add(ccc);
        Optional<Integer> reduce = a.stream().map(list -> list.stream().reduce((x, y) -> x + y).orElse(0)).reduce((x, y) -> x + y);
        System.out.println(reduce.orElse(0));
        int sum = a.stream().mapToInt(p -> p.stream().mapToInt(Integer::intValue).sum()).sum();
    }
}
