import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Assignment8Test {
	public static void main(String[] args) {
		Assignment8 assignment8 = new Assignment8();
		
		String message = "Starting execution";
		System.out.println(message);

		List<Integer> allNumbers = Collections.synchronizedList(new ArrayList<>());
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), executor)
					.thenAccept(numbers -> allNumbers.addAll(numbers));
			tasks.add(task);
		}
		
		message = "Completed";
		System.out.println(message);
		
		while (tasks.stream().filter(e -> e.isDone()).count() < 1000) {
			System.out.println(allNumbers.size() + " numbers added to the list");
		}
		//	while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000)
		//		System.out.println(numbers.size() + " numbers added to the list");
		//	System.out.println(Arrays.toString(numbers.toArray()));
		Map<Integer, Integer> countOfIntegers = allNumbers.stream()
				.collect(Collectors.toMap(k -> k, v -> 1, (oldValue, newValue) -> oldValue + 1));
		System.out.println(countOfIntegers);
	}
}