package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private NavigableMap<Customer, String> customerMap;

    public CustomerService() {
        this.customerMap = new TreeMap<>(Comparator.comparing(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        var smallestEntry = customerMap.firstEntry();
        return Map.entry(Customer.copyOf(smallestEntry.getKey()), smallestEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var nextEntry = customerMap.higherEntry(customer);
        if(nextEntry == null)
            return null;
        return Map.entry(Customer.copyOf(nextEntry.getKey()), nextEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }
}
