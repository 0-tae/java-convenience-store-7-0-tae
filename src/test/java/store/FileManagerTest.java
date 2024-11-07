package store;

import camp.nextstep.edu.missionutils.Randoms;
import store.util.FileManager;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    private final String PREFIX = "./src/test/java/store/test_resources/";

    @Test
    @DisplayName("경로 내 파일을 불러오기를 수행한다")
    public void 경로내파일을불러오기를수행한다() {
        String path = PREFIX + "readFile_test.md";
        ArrayList<String> expected = new ArrayList<>();
        expected.add("File Manager");
        expected.add("Read Test");

        assertEquals(expected, new FileManager(path).readFileAll());
    }

    @Test
    @DisplayName("경로 내 파일에 쓰기를 수행한다.")
    public void 경로내파일에쓰기를수행한다() {
        String path = PREFIX + "writeFileAll_test.md";
        ArrayList<String> contents = new ArrayList<>();

        int randomNum = Randoms.pickNumberInRange(1, 10);
        contents.add("File Manager");
        contents.add("WriteFileAll");
        contents.add("Test" + randomNum);

        FileManager fileManager = new FileManager(path);
        fileManager.writeFileAll(contents);

        List<String> expected = fileManager.readFileAll();

        assertEquals(expected, contents);
    }
}
