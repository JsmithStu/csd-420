import java.util.ArrayList;
import java.util.Random;

public class RemoveDuplicatesTest {

    /**
     * Returns a new ArrayList containing the values from the original list
     * with duplicates removed.
     *
     * @param list the original ArrayList
     * @param <E> the type of elements in the list
     * @return a new ArrayList with no duplicate values
     */
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        ArrayList<E> uniqueList = new ArrayList<>();

        for (E element : list) {
            if (!uniqueList.contains(element)) {
                uniqueList.add(element);
            }
        }

        return uniqueList;
    }

    public static void main(String[] args) {
        ArrayList<Integer> originalList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            originalList.add(random.nextInt(20) + 1);
        }

        ArrayList<Integer> noDuplicates = removeDuplicates(originalList);

        System.out.println("Original ArrayList:");
        System.out.println(originalList);

        System.out.println("\nArrayList with duplicates removed:");
        System.out.println(noDuplicates);
    }
}