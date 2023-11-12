package View.BanHang;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        // Tạo danh sách cấp 3
        List<List<List<String>>> nestedList = new ArrayList<>();

        // Tạo danh sách cấp 2
        List<List<String>> innerList1 = new ArrayList<>();
        List<List<String>> innerList2 = new ArrayList<>();

        // Tạo danh sách cấp 1 và thêm vào danh sách cấp 2
        List<String> sublist1 = new ArrayList<>();
        sublist1.add("Item A1");
        sublist1.add("Item A2");
        innerList1.add(sublist1);

        List<String> sublist2 = new ArrayList<>();
        sublist2.add("Item B1");
        sublist2.add("Item B2");
        innerList2.add(sublist2);

        // Thêm danh sách cấp 2 vào danh sách cấp 3
        nestedList.add(innerList1);
        nestedList.add(innerList2);

        // Hiển thị danh sách cấp 3
        displayNestedList(nestedList);
    }

    // Hiển thị danh sách cấp 3
    static void displayNestedList(List<List<List<String>>> nestedList) {
        for (List<List<String>> innerList : nestedList) {
            for (List<String> sublist : innerList) {
                for (String item : sublist) {
                    System.out.println(item);
                }
            }
        }
    }
}
