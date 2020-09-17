import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

    }

    public class LRU<T> {
        private List<T> list = new LinkedList<>();
        public void add(T t) {
            list.add(0, t);
        }
        public boolean delete(T t) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (t.equals(next)) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }
        public boolean update(T old, T update) {
            if (delete(old)) {
                add(update);
            } else {
                return false;
            }
            return true;
        }
    }
}
